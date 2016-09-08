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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
    String lineServer;
    String jsonTest, xmlTest;

    List<LatLng> startLatLng = new ArrayList<>();
    List<LatLng> endLatLng = new ArrayList<>();
    List<List<LatLng>> values;
    List<LatLng> linePoly;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        message = intent.getStringExtra("start");
        message2 = intent.getStringExtra("end");

        jsonTest ="{\"fields\": {\"latitude\": -25.821856, \"timestamp\": null, \"camera\": \"GP::GP CCTV R21 801A\", \"traffic\": null, \"longitude\": 28.23973819}, \"model\": \"traffic.camera_info\", \"pk\": 239}, {\"fields\": {\"latitude\": -25.819769, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 209\", \"traffic\": null, \"longitude\": 28.253443}, \"model\": \"traffic.camera_info\", \"pk\": 66}, {\"fields\": {\"latitude\": -25.814595, \"timestamp\": null, \"camera\": \"GP::CCTV 801\", \"traffic\": null, \"longitude\": 28.23482304}, \"model\": \"traffic.camera_info\", \"pk\": 230}, {\"fields\": {\"latitude\": -25.809579, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 208\", \"traffic\": null, \"longitude\": 28.259204}, \"model\": \"traffic.camera_info\", \"pk\": 27}, {\"fields\": {\"latitude\": -25.799536, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 207\", \"traffic\": null, \"longitude\": 28.262452}, \"model\": \"traffic.camera_info\", \"pk\": 35}, {\"fields\": {\"latitude\": -25.79079, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 206\", \"traffic\": null, \"longitude\": 28.268848}, \"model\": \"traffic.camera_info\", \"pk\": 85}, {\"fields\": {\"latitude\": -25.779009, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 205\", \"traffic\": null, \"longitude\": 28.272226}, \"model\": \"traffic.camera_info\", \"pk\": 40}, {\"fields\": {\"latitude\": -25.769395, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 204\", \"traffic\": null, \"longitude\": 28.274575}, \"model\": \"traffic.camera_info\", \"pk\": 91}, {\"fields\": {\"latitude\": -25.766327, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 203\", \"traffic\": null, \"longitude\": 28.275314}, \"model\": \"traffic.camera_info\", \"pk\": 38}, {\"fields\": {\"latitude\": -25.756371, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 202\", \"traffic\": null, \"longitude\": 28.275025}, \"model\": \"traffic.camera_info\", \"pk\": 18}, {\"fields\": {\"latitude\": -25.751722, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 201A\", \"traffic\": null, \"longitude\": 28.273803}, \"model\": \"traffic.camera_info\", \"pk\": 23}, {\"fields\": {\"latitude\": -25.740656, \"timestamp\": null, \"camera\": \"GP::GP CCTV N4 102\", \"traffic\": null, \"longitude\": 28.272817}, \"model\": \"traffic.camera_info\", \"pk\": 208}, {\"fields\": {\"latitude\": -25.74499, \"timestamp\": null, \"camera\": \"GP::GP CCTV N1 201\", \"traffic\": null, \"longitude\": 28.267973}, \"model\": \"traffic.camera_info\", \"pk\": 88}, {\"fields\": {\"latitude\": -25.739811, \"timestamp\": null, \"camera\": \"GP::GP CCTV N4 101\", \"traffic\": null, \"longitude\": 28.26507222}, \"model\": \"traffic.camera_info\", \"pk\": 32}";
        xmlTest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<django-objects version=\"1.0\"><object pk=\"239\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV R21 801A</field><field type=\"FloatField\" name=\"latitude\">-25.821856</field><field type=\"FloatField\" name=\"longitude\">28.23973819</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"66\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 209</field><field type=\"FloatField\" name=\"latitude\">-25.819769</field><field type=\"FloatField\" name=\"longitude\">28.253443</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"230\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::CCTV 801</field><field type=\"FloatField\" name=\"latitude\">-25.814595</field><field type=\"FloatField\" name=\"longitude\">28.23482304</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"27\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 208</field><field type=\"FloatField\" name=\"latitude\">-25.809579</field><field type=\"FloatField\" name=\"longitude\">28.259204</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"35\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 207</field><field type=\"FloatField\" name=\"latitude\">-25.799536</field><field type=\"FloatField\" name=\"longitude\">28.262452</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"85\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 206</field><field type=\"FloatField\" name=\"latitude\">-25.79079</field><field type=\"FloatField\" name=\"longitude\">28.268848</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"40\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 205</field><field type=\"FloatField\" name=\"latitude\">-25.779009</field><field type=\"FloatField\" name=\"longitude\">28.272226</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"91\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 204</field><field type=\"FloatField\" name=\"latitude\">-25.769395</field><field type=\"FloatField\" name=\"longitude\">28.274575</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"38\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 203</field><field type=\"FloatField\" name=\"latitude\">-25.766327</field><field type=\"FloatField\" name=\"longitude\">28.275314</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"18\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 202</field><field type=\"FloatField\" name=\"latitude\">-25.756371</field><field type=\"FloatField\" name=\"longitude\">28.275025</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"23\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 201A</field><field type=\"FloatField\" name=\"latitude\">-25.751722</field><field type=\"FloatField\" name=\"longitude\">28.273803</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"208\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N4 102</field><field type=\"FloatField\" name=\"latitude\">-25.740656</field><field type=\"FloatField\" name=\"longitude\">28.272817</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"88\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N1 201</field><field type=\"FloatField\" name=\"latitude\">-25.74499</field><field type=\"FloatField\" name=\"longitude\">28.267973</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object><object pk=\"32\" model=\"traffic.camera_info\"><field type=\"CharField\" name=\"camera\">GP::GP CCTV N4 101</field><field type=\"FloatField\" name=\"latitude\">-25.739811</field><field type=\"FloatField\" name=\"longitude\">28.26507222</field><field type=\"IntegerField\" name=\"traffic\"><None></None></field><field type=\"DateTimeField\" name=\"timestamp\"><None></None></field></object></django-objects>";

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
        Toast.makeText(getBaseContext(),"DONE!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentsView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.info_window_title));
                TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.info_window_snippet));
                tvTitle.setText(marker.getTitle());
                tvSnippet.setText(marker.getSnippet());

                return myContentsView;
            }
        });
      //  new ServerRequest(this).execute();

        processServerResponse(xmlTest);




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
                GoogleRequest googleRequest = new GoogleRequest(message, message2);
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
                    lineServer = new GoogleJSONParser(responseOutput.toString()).returnEncodedPoly();
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
                    //    Toast.makeText(getBaseContext(),lineServer, Toast.LENGTH_LONG).show();
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

    private class ServerRequest extends AsyncTask<String, Void, Void>
    {
        private Context context;

        public ServerRequest(Context c)
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
        protected Void doInBackground(String... strings)
        {
            URL url = null;
            try {
                url = new URL("http://192.168.43.232:8000/polyline/"+ URLEncoder.encode(lineServer, "UTF-8"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
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

                String response = responseOutput.toString();
            }
            catch(IOException error)
            {

            }

            return null;
        }
    }

    public void processServerResponse(String xml)
    {
        List<Double> lat = new ArrayList<>();
        List<Double> lng = new ArrayList<>();
     //   List<String> traffic = new ArrayList<>();
      //  List<String> timestamp = new ArrayList<>();
        List<String> camid = new ArrayList<>();


        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xml));

            int event = xmlPullParser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT)
            {
                String name = xmlPullParser.getName();
                switch(event)
                {
                    case XmlPullParser.START_TAG:
                        if(name.equals("field")) {
                            String startLat = "", startLng = "", trafficLevel = "", time = "", camera = "";

                            try {
                                if(xmlPullParser.getAttributeValue(null, "name").equals("latitude")) {
                                    startLat = xmlPullParser.nextText();
                                    lat.add(new Double(Double.parseDouble(startLat)));
                                }
                                else if(xmlPullParser.getAttributeValue(null, "name").equals("longitude")) {
                                    startLng = xmlPullParser.nextText();
                                    lng.add(new Double(Double.parseDouble(startLng)));
                                }
                                else if(xmlPullParser.getAttributeValue(null, "name").equals("camera")) {
                                    camera = xmlPullParser.nextText();
                                    camid.add(camera);
                                }
                                else
                                {

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //if(!startLat.equals(""))
                              //  Toast.makeText(getBaseContext(),startLat, Toast.LENGTH_LONG).show();
                            //else
                              //  Toast.makeText(getBaseContext(),startLng, Toast.LENGTH_LONG).show();



                        }
                        break;
                }
                try
                {
                    event = xmlPullParser.next();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0; i < lat.size(); ++i)
                 mMap.addMarker(new MarkerOptions().position(new LatLng(lat.get(i), lng.get(i))).title(camid.get(i)).snippet("Halla\nMan!!!!!!"));

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
