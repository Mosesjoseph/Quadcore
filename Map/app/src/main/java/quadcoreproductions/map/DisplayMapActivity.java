package quadcoreproductions.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Themba Mbhele on 2016/08/03.
 */
public class DisplayMapActivity extends FragmentActivity implements OnMapReadyCallback
{
    GoogleMap mMap;
    ProgressDialog progress;
    String message;
    String message2;

    List<LatLng> startLatLng = new ArrayList<>();
    List<LatLng> endLatLng = new ArrayList<>();
    List<List<LatLng>> values;
    List<LatLng> linePoly;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
      //  Intent intent = getIntent();
  //      message = intent.getStringExtra("origin");
    //    message2 = intent.getStringExtra("destination");
      //  new GetClass(this).execute();
       // TextView text = (TextView)findViewById(R.id.textView);
        //text.setTextSize(40);
        //text.setText(message);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        System.out.println("sizeeeeeee2: "+startLatLng.size());
        mMap = googleMap;
        new GetClass(this).execute();


        // Add a marker in Sydney and move the camera
    //    LatLng sydney = new LatLng(-25.755926, 28.231105);
       // LatLng sydney = new LatLng(startLatLng.get(0).latitude, startLatLng.get(0).longitude);
         // LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("CurrentLocation"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        //if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
          //  return;
        //}
        //mMap.setMyLocationEnabled(true);
    }

    public void  sendGetRequest(View view)
    {
        new GetClass(this).execute();
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
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params)
        {
            try {
             //   final TextView textView = (TextView) findViewById(R.id.textView);
            //    String urlString = "https://maps.googleapis.com/maps/api/directions/json?";
            //    String origin = "";
            //    String destination = "";
            //    String apiKey = "AIzaSyBxzMOzDddAIUKR3RlINgbhtTReEGCvEKI";
            //    urlString+="origin=pretoria&destination=johannesburg&key=";
            //    urlString+=apiKey;
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
                    values = new GoogleJSONParser(responseOutput.toString()).parseJSON();
                    linePoly = new GoogleJSONParser(responseOutput.toString()).decode();
                    startLatLng = new ArrayList<>(values.get(0));
                    endLatLng = new ArrayList<>(values.get(1));
                    String json = responseOutput.toString();
                    if(json == "")
                        return null;


                }
                catch(JSONException error)
                {
                    error.printStackTrace();
                }

                DisplayMapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  LatLng test = new LatLng(startLatLng.get(0).latitude, startLatLng.get(0).longitude);
                      //  mMap.addMarker(new MarkerOptions().position(test).title("CurrentLocation"));
                      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, 15));
                        mMap.addPolyline(new PolylineOptions().addAll(linePoly));
                     //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(linePoly.get(0), 15));
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
}
