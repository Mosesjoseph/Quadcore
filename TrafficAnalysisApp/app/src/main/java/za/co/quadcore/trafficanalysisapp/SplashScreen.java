package za.co.quadcore.trafficanalysisapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SplashScreen extends Activity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    public void beginRoutes(View view)
    {
        Intent intent = new Intent(this, RoutesActivity.class);
        startActivity(intent);
    }
}
