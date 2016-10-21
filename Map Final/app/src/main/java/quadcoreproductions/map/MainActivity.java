package quadcoreproductions.map;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.*;
import android.widget.Toast;

/**
 * Created by Quadcore Productions on 2016/08/03.
 */
public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        databaseSetUp();
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    public void databaseSetUp()
    {
        //Open database if it exists and if it doesn't exist, create it.
        sqLiteDatabase = openOrCreateDatabase("Quadcore_CameraAnalysis", MODE_PRIVATE, null);
        //create routes table if it does not exist
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS routes(startLocation text, endLocation text);");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.add_icon)
        {
            Intent intent = new Intent(this, AddRouteActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.view_routes_icon)
        {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM routes", null);
            if(cursor.getCount() < 1)
            {
                Toast.makeText(getApplicationContext(), "There are no stored routes", Toast.LENGTH_LONG).show();
                return true;
            }
            else
            {
                Intent intent = new Intent(this, StoredRoutes.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
