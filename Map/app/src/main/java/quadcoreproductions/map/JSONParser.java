package quadcoreproductions.map;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Themba Mbhele on 2016/07/24.
 */
public interface JSONParser
{
    public List<List<LatLng>> parseJSON() throws JSONException;
}
