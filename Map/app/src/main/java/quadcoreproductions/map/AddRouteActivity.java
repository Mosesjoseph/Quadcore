package quadcoreproductions.map;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Themba Mbhele on 2016/08/03.
 */
public class AddRouteActivity extends AppCompatActivity
{
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_route_activity);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Button button = (Button)findViewById(R.id.search_button);
        button.setTypeface(typeface);
    }

    public void sendRequestParameters(View view)
    {
        Intent intent = new Intent(this, DisplayMapActivity.class);
        EditText start = (EditText)findViewById(R.id.startLocation);
        EditText end = (EditText)findViewById(R.id.endLocation);
        String startParameter = start.getText().toString();
        String endParameter = end.getText().toString();
        intent.putExtra("origin", startParameter);
        intent.putExtra("destination", endParameter);
        finish();
        startActivity(intent);
    }
}
