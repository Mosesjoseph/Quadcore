package quadcoreproductions.cameraanalysis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Themba Mbhele on 2016/07/19.
 */
public class HomeLayout extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        return view;
    }
}
