package quadcoreproductions.map;

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

            //getpolyline
            JSONObject polyLine = jsonRoute.getJSONObject("overview_polyline");

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

    public List<LatLng> decode() throws JSONException
    {
        List<LatLng> poly = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonRoutes = jsonObject.getJSONArray("routes");

        JSONObject jsonRoute = jsonRoutes.getJSONObject(0);

        //getpolyline
        JSONObject polyLine = jsonRoute.getJSONObject("overview_polyline");
        final String line = polyLine.getString("points");

        int length = line.length();
        int i = 0;
        int lat = 0;
        int lng = 0;

        while(i < length)
        {
            int b;
            int shift = 0;
            int result = 0;
            do
            {
                b = line.charAt(i++) - 63;
                result |= (b & 0x1f) << shift;
                shift+=5;
            }
            while(b >= 0x20);
            int dist = ((result & 1) != 0 ? ~(result >>1) : (result >> 1));
            lat+=dist;

            shift = 0;
            dist = 0;
            do
            {
                b = line.charAt(i++) - 63;
                result |= (b & 0x1f) << shift;
                shift+=5;
            }
            while(b >= 0x20);
            int dlng = ((result&1) != 0 ? ~(result >> 1) : (result >> 1));
            lng+=dlng;

            poly.add(new LatLng(lat/100000d, lng/100000d));
        }
        return poly;
    }
}
