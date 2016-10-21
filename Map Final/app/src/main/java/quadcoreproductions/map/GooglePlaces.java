package quadcoreproductions.map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Quadcore Productions
 */
public class GooglePlaces
{
    private static final String TAG = GooglePlaces.class.getSimpleName();
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyBxzMOzDddAIUKR3RlINgbhtTReEGCvEKI";

    public ArrayList<String> autocomplete(String input)
    {
        ArrayList<String> resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try
        {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE+TYPE_AUTOCOMPLETE+OUT_JSON);
            sb.append("?key="+API_KEY);
            sb.append("&components=country:za");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection)url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while((read = in.read(buff)) != -1)
                jsonResults.append(buff, 0, read);
        }
        catch(MalformedURLException error)
        {

        }
        catch(IOException error)
        {

        }
        finally
        {
            if(conn != null)
                conn.disconnect();
        }

        try
        {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            resultList = new ArrayList<String>(predsJsonArray.length());
            for(int i = 0; i < predsJsonArray.length(); ++i)
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
        }
        catch(JSONException error)
        {

        }
        return  resultList;
    }
}
