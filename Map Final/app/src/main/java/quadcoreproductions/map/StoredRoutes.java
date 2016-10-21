package quadcoreproductions.map;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Quadcore Productions on 2016/08/27.
 */
public class StoredRoutes extends AppCompatActivity
{
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> test;
    private ArrayList<String> startLocations;
    private ArrayList<String> endLocations;
    private String selectedChoice;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_list_view);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.stored_routes);

        ArrayList<String> planetList = new ArrayList<String>();
        startLocations = new ArrayList<String>();
        endLocations = new ArrayList<String>();
        test = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.row, planetList);

        Cursor resultList = sqLiteDatabase.rawQuery("Select * from routes", null);

        if(resultList.moveToFirst())
        {
            do
            {
                String startLocation = resultList.getString(0);
                startLocations.add(startLocation);
                String endLocation = resultList.getString(1);
                endLocations.add(endLocation);

                for(int i = 0; i < startLocation.length(); ++i)
                {
                    if(startLocation.charAt(i) == ',')
                        startLocation = startLocation.substring(0, i);
                }

                for(int i = 0; i < endLocation.length(); ++i)
                {
                    if(endLocation.charAt(i) == ',')
                        endLocation = endLocation.substring(0, i);
                }

                arrayAdapter.add(startLocation + " - " + endLocation);
                test.add(startLocation + " - " + endLocation);
            }
            while(resultList.moveToNext());
        }


        arrayAdapter.add("Ceres");
        arrayAdapter.add("Pluto");
        arrayAdapter.add("Haumea");

    //    listView.setAdapter(arrayAdapter);
        listView.setAdapter(new CustomAdapter(this, test));
    //    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //        @Override
    //        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    //        {
    //            Toast.makeText(getApplicationContext(), selectedChoice, Toast.LENGTH_LONG).show();

      //          selectedChoice = ((TextView)view).getText().toString();
    //            showPopUpMenu(view);
    //        }
    //    });

    }

    public void showPopUpMenu(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        PopUpMenuEventHandler popUpMenuEventHandler = new PopUpMenuEventHandler(getApplicationContext());
        popupMenu.setOnMenuItemClickListener(popUpMenuEventHandler);
        menuInflater.inflate(R.menu.manage_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    public class PopUpMenuEventHandler implements PopupMenu.OnMenuItemClickListener
    {
        Context context;
        public PopUpMenuEventHandler(Context context)
        {
            this.context = context;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item)
        {
            if(item.getItemId() == R.id.edit_route)
            {
                String start = "", end = "";
                for(int i = 0; i < selectedChoice.length(); ++i)
                {
                    if(selectedChoice.charAt(i) == '-')
                    {
                        start = selectedChoice.substring(0, i-1);
                        end = selectedChoice.substring(i + 2, selectedChoice.length());
                        break;
                    }
                }

                String temp = "", origin = "", destination = "";
                CharSequence charSequence = (CharSequence)start;
                for(int i = 0; i < startLocations.size(); ++i)
                {
                    temp = startLocations.get(i);
                    if(temp.contains(charSequence))
                    {
                        origin = temp;
                        destination = endLocations.get(i);
                        break;
                    }
                }
                // Toast.makeText(getBaseContext(),origin + "-" + destination , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(StoredRoutes.this, ModifyRoute.class);
                intent.putExtra("modify_start", origin);
                intent.putExtra("modify_end", destination);
                startActivity(intent);
                return true;
            }
            else if(item.getItemId() == R.id.delete_route)
            {
                String start = "", end = "";
                for(int i = 0; i < selectedChoice.length(); ++i)
                {
                    if(selectedChoice.charAt(i) == '-')
                    {
                        start = selectedChoice.substring(0, i-1);
                        end = selectedChoice.substring(i + 2, selectedChoice.length());
                        break;
                    }
                }

                String temp = "", origin = "", destination = "";
                CharSequence charSequence = (CharSequence)start;
                for(int i = 0; i < startLocations.size(); ++i)
                {
                    temp = startLocations.get(i);
                    if(temp.contains(charSequence))
                    {
                        origin = temp;
                        destination = endLocations.get(i);
                        break;
                    }
                }

                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
                sqLiteDatabase.delete("routes", "startLocation = ? AND endLocation = ?", new String[]{origin, destination});
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM routes", null);
                if(cursor.getCount() < 1)
                {
                    finish();
                    return true;
                }
                else
                {
                    Intent intent = new Intent(StoredRoutes.this, StoredRoutes.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

            }
            else if(item.getItemId() == R.id.get_report)
            {
                String start = "", end = "";
                for(int i = 0; i < selectedChoice.length(); ++i)
                {
                    if(selectedChoice.charAt(i) == '-')
                    {
                        start = selectedChoice.substring(0, i-1);
                        end = selectedChoice.substring(i + 2, selectedChoice.length());
                        break;
                    }
                }

                String temp = "", origin = "", destination = "";
                CharSequence charSequence = (CharSequence)start;
                for(int i = 0; i < startLocations.size(); ++i)
                {
                    temp = startLocations.get(i);
                    if(temp.contains(charSequence))
                    {
                        origin = temp;
                        destination = endLocations.get(i);
                        break;
                    }
                }
               // Toast.makeText(getBaseContext(),origin + "-" + destination , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(StoredRoutes.this, DisplayMapActivity.class);
                intent.putExtra("start", origin);
                intent.putExtra("end", destination);
                startActivity(intent);
                return true;
            }
            return false;
        }
    }

    public class CustomAdapter extends BaseAdapter
    {
        private LayoutInflater inflater = null;
        ArrayList<String> routes;
        Context context;
        public CustomAdapter(StoredRoutes storedRoutes, ArrayList<String> routes)
        {
            this.routes = routes;
            context = storedRoutes;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //  Toast.makeText(getApplicationContext(), "in here", Toast.LENGTH_LONG).show();
        }

        @Override
        public int getCount() {
            return routes.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class Holder
        {
            TextView tv;
            ImageView img;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup)
        {
           // Toast.makeText(getApplicationContext(), "halla", Toast.LENGTH_LONG).show();
            Holder holder = new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.custom_list_view, null);
            holder.tv = (TextView)rowView.findViewById(R.id.storedRoute);
            holder.img = (ImageView)rowView.findViewById(R.id.customImage);

            holder.tv.setText(routes.get(i));
            holder.img.setImageResource(R.drawable.yellow_road_sign);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedChoice = routes.get(i);
                  //  Toast.makeText(getApplicationContext(), selectedChoice, Toast.LENGTH_LONG).show();
                    showPopUpMenu(view);
                }
            });
            return rowView;
        }
    }
}
