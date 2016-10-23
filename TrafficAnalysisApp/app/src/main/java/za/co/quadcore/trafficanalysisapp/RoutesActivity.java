package za.co.quadcore.trafficanalysisapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Quadcore Productions on 2016/08/03.
 * Updated 2016/10/21
 */

public class RoutesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private String selectedRoute;
    private ArrayList<String> routes;
    private ArrayList<String> startLocations;
    private ArrayList<String> endLocations;
    private ListView routesListView;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        databaseSetUp();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationMenuLoad();
    }

    public void navigationMenuLoad()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();

        routesListView = (ListView)findViewById(R.id.routes_list);

        routes = new ArrayList<>();
        startLocations = new ArrayList<>();
        endLocations = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);

        Cursor resultList = sqLiteDatabase.rawQuery("Select * from routes", null);

        if(resultList.moveToFirst())
        {
            do
            {
                String routeName = resultList.getString(0);
                routes.add(routeName);
                String startLocation = resultList.getString(1);
                startLocations.add(startLocation);
                String endLocation = resultList.getString(2);
                endLocations.add(endLocation);
            }

            while(resultList.moveToNext());
        }

        resultList.close();

        routesListView.setAdapter(new CustomAdapter(this, routes));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.routes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addroute)
        {
            addRoute();
            navigationMenuLoad();
            return true;
        }
        else if(id == R.id.action_modifyroute)
        {
            modifyRoute();
            navigationMenuLoad();
            return true;
        }
        else if(id == R.id.action_deleteroute)
        {
            deleteRoute();
            navigationMenuLoad();
            return true;
        }

        return false;
    }

    public boolean addRoute()
    {
        Intent intent = new Intent(this, AddRoute.class);
        startActivity(intent);
        return true;
    }

    public boolean modifyRoute()
    {
        Intent intent = new Intent(this, ModifyRoute.class);

        String name = "", origin = "", destination = "";

        for(int i = 0; i < routes.size(); ++i)
        {
            if(routes.get(i).contentEquals(selectedRoute))
            {
                name = routes.get(i);
                origin = startLocations.get(i);
                destination = endLocations.get(i);
                break;
            }
        }

        intent.putExtra("modify_name", name);
        intent.putExtra("modify_start", origin);
        intent.putExtra("modify_end", destination);
        startActivity(intent);
        return true;
    }

    public boolean deleteRoute()
    {
        Context context = this.getApplicationContext();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Delete route
                        String name = "", origin = "", destination = "";
                        for(int i = 0; i < routes.size(); ++i)
                        {
                            if(routes.get(i).contentEquals(selectedRoute))
                            {
                                name = routes.get(i);
                                origin = startLocations.get(i);
                                destination = endLocations.get(i);
                                break;
                            }
                        }

                        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
                        sqLiteDatabase.delete("routes", "routeName = ? AND startLocation = ? AND endLocation = ?", new String[]{name, origin, destination});
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM routes", null);
                        if(cursor.getCount() < 1)
                        {
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(RoutesActivity.this, RoutesActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do nothing
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this route?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        if(mMap != null) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location location) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                }
            });
        }
    }

    public void databaseSetUp()
    {
        SQLiteDatabase sqLiteDatabase;
        //Open database if it exists and if it doesn't exist, create it.
        sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
        //create routes table if it does not exist
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS routes(routeName text, startLocation text, endLocation text);");
    }

    public boolean displayRouteMap()
    {
        findViewById(R.id.action_modifyroute).setEnabled(true);
        findViewById(R.id.action_deleteroute).setEnabled(true);

        Boolean routeExists = false;

        String origin = "", destination = "";

        for(int i = 0; i < routes.size(); ++i)
        {
            if(routes.get(i).equals(selectedRoute))
            {
                origin = startLocations.get(i);
                destination = endLocations.get(i);
                break;
            }
        }

        Intent intent = new Intent(this, DisplayMapActivity.class);
        intent.putExtra("start", origin);
        intent.putExtra("end", destination);
        startActivity(intent);
        return true;
    }

    public class CustomAdapter extends BaseAdapter
    {
        private LayoutInflater inflater = null;
        ArrayList<String> routes;
        Context context;
        public CustomAdapter(RoutesActivity storedRoutes, ArrayList<String> routes)
        {
            this.routes = routes;
            context = storedRoutes;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //  Toast.makeText(getApplicationContext(), "in here", Toast.LENGTH_LONG).show();
        }

        @Override
        public int getCount() {
            return routes.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class Holder
        {
            TextView tv;
            ImageView img;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup)
        {
            // Toast.makeText(getApplicationContext(), "halla", Toast.LENGTH_LONG).show();
            Holder holder = new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.custom_list_view, null);
            holder.tv = (TextView)rowView.findViewById(R.id.storedRoute);
            holder.img = (ImageView)rowView.findViewById(R.id.customImage);

            holder.tv.setText(routes.get(i));
            holder.img.setImageResource(R.drawable.yellow_road_sign);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    displayRouteMap();
                    selectedRoute = routes.get(i);
                }
            });
            return rowView;
        }
    }

}
