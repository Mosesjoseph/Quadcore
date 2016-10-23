package za.co.quadcore.trafficanalysisapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

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
 * Created by Quadcore Productions on 2016/09/08.
 * Updated 2016/10/21
 */
public class ModifyRoute extends AppCompatActivity
{
    Toolbar toolbar;

    private String modify_name, modify_start, modify_end;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyBxzMOzDddAIUKR3RlINgbhtTReEGCvEKI";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        Intent intent = getIntent();
        modify_name = intent.getStringExtra("modify_name");
        modify_start = intent.getStringExtra("modify_start");
        modify_end = intent.getStringExtra("modify_end");

        // Toast.makeText(getApplicationContext(), modify_start, Toast.LENGTH_LONG).show();
        // Toast.makeText(getApplicationContext(), modify_end, Toast.LENGTH_LONG).show();

        /*toolbar = (Toolbar)findViewById(R.id.toolbar);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back_white_18dp);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Button button = (Button)findViewById(R.id.search_button);
        button.setTypeface(typeface);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autocompleteStart);
        autoCompleteTextView.setAdapter(new PlacesAutoComplete(this, R.layout.autocomplete_list_item));

        AutoCompleteTextView autoCompleteTextViewEnd = (AutoCompleteTextView)findViewById(R.id.autocompleteEnd);
        autoCompleteTextViewEnd.setAdapter(new PlacesAutoComplete(this, R.layout.autocomplete_list_item));
    }

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

    class PlacesAutoComplete extends ArrayAdapter<String> implements Filterable
    {
        ArrayList<String> resultList;
        Context mContext;
        int mResource;

        public PlacesAutoComplete(Context context, int resource)
        {
            super(context, resource);
            mContext = context;
            mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view;
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(position != (resultList.size()-1))
                view = inflater.inflate(R.layout.autocomplete_list_item, null);
            else
                view = inflater.inflate(R.layout.place_autocomplete_item_powered_by_google, null);

            if(position != (resultList.size()-1))
            {
                TextView autocompleteTextView = (TextView)view.findViewById(R.id.autocompleteText);
                autocompleteTextView.setText(resultList.get(position));
            }
            else
            {

            }
            return view;
        }

        @Override
        public int getCount()
        {
            return resultList.size();
        }

        @Override
        public String getItem(int position)
        {
            return resultList.get(position);
        }

        @Override
        public Filter getFilter()
        {
            Filter filter = new Filter()
            {

                @Override
                protected FilterResults performFiltering(CharSequence charSequence)
                {
                    FilterResults filterResults = new FilterResults();
                    if(charSequence != null)
                    {
                        resultList = autocomplete(charSequence.toString());
                        resultList.add("footer");
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults)
                {
                    if(filterResults != null && filterResults.count > 0)
                        notifyDataSetChanged();
                    else
                        notifyDataSetInvalidated();
                }
            };
            return filter;
        }
    }

    public void modifyRoute(View view)
    {
        //get route name
        EditText name = (EditText)findViewById(R.id.edtRouteName);
        //get start location
        EditText start = (EditText)findViewById(R.id.autocompleteStart);
        //get end location
        EditText end = (EditText)findViewById(R.id.autocompleteEnd);

        String nameParameter = name.getText().toString();
        String startParameter = start.getText().toString();
        String endParameter = end.getText().toString();

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();
        if(!nameParameter.isEmpty())
            contentValues.put("routeName", startParameter);
        if(!startParameter.isEmpty())
            contentValues.put("startLocation", startParameter);
        if(!endParameter.isEmpty())
            contentValues.put("endLocation", endParameter);

        if(!nameParameter.isEmpty() && !startParameter.isEmpty() && !endParameter.isEmpty())
            sqLiteDatabase.update("routes", contentValues, " routeName = '" + modify_name + "'startLocation='"+modify_start+"' AND endLocation="+modify_end+"'", null);
        else if(!nameParameter.isEmpty() && !startParameter.isEmpty() && endParameter.isEmpty())
            sqLiteDatabase.update("routes", contentValues, " routeName = '" + modify_name + "' startLocation= '"+modify_start + "'", null);
        else if(!nameParameter.isEmpty() && startParameter.isEmpty() && !endParameter.isEmpty())
            sqLiteDatabase.update("routes", contentValues, " routeName = '" + modify_name + "' endLocation= '" + modify_end + "'", null);
        else if(nameParameter.isEmpty() && !startParameter.isEmpty() && !endParameter.isEmpty())
            sqLiteDatabase.update("routes", contentValues, "startLocation='"+modify_start+"' AND endLocation="+modify_end+"'", null);
        else if(nameParameter.isEmpty() && !startParameter.isEmpty() && endParameter.isEmpty())
            sqLiteDatabase.update("routes", contentValues, "startLocation= '"+modify_start + "'", null);
        else if(nameParameter.isEmpty() && startParameter.isEmpty() && !endParameter.isEmpty())
            sqLiteDatabase.update("routes", contentValues, " endLocation= '" + modify_end + "'", null);

        Intent intent = new Intent(this, RoutesActivity.class);
        startActivity(intent);
        finish();
    }
}
