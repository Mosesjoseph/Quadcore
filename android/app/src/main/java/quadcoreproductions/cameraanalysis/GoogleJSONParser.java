package quadcoreproductions.cameraanalysis;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Themba Mbhele on 2016/07/24.
 */
public class GoogleJSONParser implements JSONParser
{
    String json = "";

    public GoogleJSONParser(String json)
    {
        this.json = json;
    }
    @Override
    public List<List<LatLng>> parseJSON() throws JSONException
    {
        if(json == "")
            return null;

        List<LatLng> startLatLng = new ArrayList<>();
        List<LatLng> endLatLng = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonRoutes = jsonObject.getJSONArray("routes");

        for(int i = 0; i < jsonRoutes.length(); ++i)
        {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);

            //get the legs of the route
            JSONArray routeLegs = jsonRoute.getJSONArray("legs");

            //get the steps of the route
            JSONObject routeSteps = routeLegs.getJSONObject(0);
            JSONArray steps = routeSteps.getJSONArray("steps");

            for(int j = 0; j < steps.length(); ++j)
            {
                //loop through steps to get to get their latlng values
                JSONObject step = steps.getJSONObject(j);
                JSONObject slatlng = step.getJSONObject("start_location");
                JSONObject elatlong = step.getJSONObject("end_location");
                startLatLng.add(new LatLng(slatlng.getDouble("lat"), slatlng.getDouble("lng")));
                endLatLng.add(new LatLng(elatlong.getDouble("lat"), elatlong.getDouble("lng")));
            }
        }
        List<List<LatLng>> returnList = new ArrayList<>();
        returnList.add(startLatLng);
        returnList.add(endLatLng);
        return returnList;
    }
}
