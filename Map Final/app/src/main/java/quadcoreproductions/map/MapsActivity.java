package quadcoreproductions.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_activity);

        databaseSetUp();

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.add_icon)
        {
            Intent intent = new Intent(this, AddRouteActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.view_routes_icon)
        {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM routes", null);
            if(cursor.getCount() < 1)
            {
                Toast.makeText(getApplicationContext(), "There are no stored routes", Toast.LENGTH_LONG).show();
                return true;
            }
            else
            {
                Intent intent = new Intent(this, StoredRoutes.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        //LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
        //String provider = locationManager.getBestProvider(criteria, false);
       // Location location = locationManager.getLastKnownLocation(provider);

        // Add a marker in Sydney and move the camera
    //    LatLng sydney = new LatLng(-25.755926, 28.231105);
      //  LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("CurrentLocation"));
   //     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));

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
        //Open database if it exists and if it doesn't exist, create it.
        sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
        //create routes table if it does not exist
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS routes(startLocation text, endLocation text);");
    }
}
