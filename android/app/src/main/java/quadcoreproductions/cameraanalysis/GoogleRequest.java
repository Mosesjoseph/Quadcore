package quadcoreproductions.cameraanalysis;

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
        return url + "origin=" + origin + "&destination=" + destination + "&key=" + apiKey;
    }
}
