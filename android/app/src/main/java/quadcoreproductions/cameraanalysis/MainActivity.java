package quadcoreproductions.cameraanalysis;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.net.RouteInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String[] listItems ={"Themba", "Moses", "Android", "Python", "Wassup", "Selenium", "PhantomJS", "BS4", "Halla!!!!!!!!!!"};
    HttpURLConnection client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean test = false;
        boolean test2 = false;

        try
        {
           test = connectToServer();
           test2 = sendToServer();
            getServerResponse();
        }
        catch (IOException error)
        {
            error.printStackTrace();
        }
        //}

        if(test == true)
            listItems[0] = "successful";
        else
            listItems[0] = "failed";

        if(test2 == true)
            listItems[1] = "successful";
        else
            listItems[1] = "failed";


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_listview, listItems);
        ListView listView = (ListView)findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(arrayAdapter);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            //}
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_navigation) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.modify_route)
        {
            // Handle the camera action
        }
        else if (id == R.id.add_route)
        {
           // AddRouteActivity addRouteActivity = new AddRouteActivity();
          //  fragmentTransaction.replace(R.id.mainFragment, new quadcoreproductions.cameraanalysis.AddRouteActivity());
            fragmentTransaction.commit();
        }
        else if (id == R.id.delete_route)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    String requestParameters = "camera=";
    public boolean connectToServer()
    {
        String server ="http://10.0.0.1:8000";
        URL url;

        //try to create a connection
        try {
            try {
                 url = new URL(server);
            }
            catch(MalformedURLException error)
            {
                return  false;
            }
            client = (HttpURLConnection) url.openConnection();
            return true;
        }
        catch(IOException error)
        {
            return false;
        }
    }

    public boolean sendToServer() throws ProtocolException
    {
        client.setRequestMethod("GET");
        client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        client.setDoInput(true);
        client.setDoOutput(true);

        //Send Request
        try {
            DataOutputStream wr = new DataOutputStream(client.getOutputStream());
         //   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
           // wr.writeBytes("/getcamera/TEM212");
            //wr.flush();
            //wr.close();

            return true;

        }
        catch(IOException error)
        {
            return false;
        }
    }

    public void getServerResponse() throws IOException
    {
        InputStream is = client.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuffer response = new StringBuffer();
        while((line = br.readLine()) != null)
        {
            response.append(line);
            response.append('\r');
        }
        br.close();
        int itemsLength = listItems.length;
        listItems[itemsLength-1] = response.toString();
    }

    public void disconnectToServer()
    {
        client.disconnect();
    }

    public String getURL(String origin, String destination) throws UnsupportedEncodingException
    {
        String originParameter;
        String destinationParameter;

        originParameter = URLEncoder.encode(origin, "utf-8");
        destinationParameter = URLEncoder.encode(destination, "utf-8");

        String url = "https://maps.googleapis.com/maps/api/dir" + "origin=" + originParameter + "&destination=" + destination + "&key=";
        return url;

    }

    private class downloadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection googleServer = null;
                InputStream is;
                StringBuffer buffer = null;
                BufferedReader br;
                try {
                    googleServer = (HttpURLConnection) url.openConnection();
                    is = googleServer.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    buffer = new StringBuffer();
                    String line = "";

                    while ((line = br.readLine()) != null)
                        buffer.append(line);
                } catch (IOException error) {

                }

                return buffer.toString();
            } catch (MalformedURLException error) {

            }
            return "";
        }
    }

    public void parseJSONGoogle(String googleData) throws JSONException
    {
        if(googleData == null)
            return;

        List<LatLng> startLatLong = new ArrayList<>();
        List<LatLng> endLatLong = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(googleData);
        JSONArray jsonArray = jsonObject.getJSONArray("routes");

        //loop through route
        for(int i = 0; i < jsonArray.length(); ++i)
        {

            JSONObject jsonRoute = jsonArray.getJSONObject(i);
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
            JSONObject jsonEndLocation =  jsonLeg.getJSONObject("end_location");

            //get the latitude and longitude
            LatLng latLngStart = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            LatLng latLngEnd = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));

            startLatLong.add(latLngStart);
            endLatLong.add(latLngEnd);

        }

        //us latlong values to see which cameras are in the path and send those cameras to the server
    }
}


