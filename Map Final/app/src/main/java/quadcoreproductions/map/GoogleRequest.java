package quadcoreproductions.map;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Themba Mbhele on 2016/07/24.
 */
public class GoogleRequest implements Request
{
    String url = "https://maps.googleapis.com/maps/api/directions/json?";
    String origin = "";
    String destination = "";
    String apiKey = "AIzaSyBxzMOzDddAIUKR3RlINgbhtTReEGCvEKI";

    public GoogleRequest(String origin, String destination)
    {
       this.origin = origin;
        this.destination = destination;
    }

    @Override
    public String getUrl()
    {
        try {
            return url + "origin=" + URLEncoder.encode(origin, "UTF-8") + "&destination=" + URLEncoder.encode(destination, "UTF-8") + "&key=" + apiKey;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
