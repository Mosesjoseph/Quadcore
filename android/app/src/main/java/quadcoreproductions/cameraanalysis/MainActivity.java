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

    String[] listItems ={"", "", "Android", "Python", "Wassup", "Selenium", "PhantomJS", "BS4", "Halla!!!!!!!!!!"};
    HttpURLConnection client;
 List<LatLng> startLatLng = new ArrayList<>();
    List<LatLng> endLatLng = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean test = false;
        boolean test2 = false;

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_listview, listItems);
        ListView listView = (ListView)findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(arrayAdapter);
        }

	 public void  sendGetRequest(View view)
	 {
		new GetClass(this).execute();
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

     private class GetClass extends AsyncTask<String, Void, Void>
   {
       private Context context;

       public GetClass(Context c)
       {
           this.context = c;
       }

       protected  void onPreExecute()
       {
           progress = new ProgressDialog(this.context);
           progress.setMessage("Loading");
           progress.show();
       }

       @Override
       protected Void doInBackground(String... params)
       {
           try {
               final TextView textView = (TextView) findViewById(R.id.textView);
               GoogleRequest googleRequest = new GoogleRequest("pretoria", "johannesburg");
               URL url = new URL(googleRequest.getUrl());
               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
               httpURLConnection.setRequestMethod("GET");
               httpURLConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
               httpURLConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US, en;0.5");

               int responseCode = httpURLConnection.getResponseCode();

               final StringBuilder output = new StringBuilder("Request URL " + url);
               output.append(System.getProperty("line.separator") + "Response Code " +responseCode);
               output.append(System.getProperty("line.seperator") + "Type " + "GET");
               BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
               String line = "";
               StringBuilder responseOutput = new StringBuilder();
               System.out.println("output==============" + br);
               while((line = br.readLine()) != null)
                   responseOutput.append(line);
               br.close();

           //    System.out.println(responseOutput.toString());

               output.append(System.getProperty("line.seperator")+ "Response " + System.getProperty("line.seperator") + System.getProperty("line.seperator") + responseOutput.toString());
               try
               {
                   //parseJSONGoogle(responseOutput.toString());
                   List<List<LatLng>> values = new GoogleJSONParser(responseOutput.toString()).parseJSON();
                   startLatLng = new ArrayList<>(values.get(0));
                   endLatLng = new ArrayList<>(values.get(1));
               }
               catch(JSONException error)
               {
                   error.printStackTrace();
               }

               MainActivity.this.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       textView.setText(startLatLng.get(0).toString());
                       progress.dismiss();
                   }
               });
           }
           catch(IOException error)
           {

           }

           return null;
       }
   
   

   
   

   
   
}


