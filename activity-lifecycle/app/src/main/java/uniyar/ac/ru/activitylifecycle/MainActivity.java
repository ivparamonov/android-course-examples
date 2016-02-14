package uniyar.ac.ru.activitylifecycle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getSimpleName(), "onCreate()");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName(), "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName(), "onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "onDestroy()");

    }
}
