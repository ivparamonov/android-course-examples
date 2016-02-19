package ru.ac.uniyar.dots;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import ru.ac.uniyar.dots.Dots;

public class DotsActivity extends AppCompatActivity {

    private final static int DOT_SIZE = 20;

    private final Dots dots = new Dots();
    private final Random rand = new Random();
    private DotView dotView;

    public void generateDot(int color) {
        dots.addDot(new Dot(rand.nextFloat() * dotView.getWidth(),
                rand.nextFloat() * dotView.getHeight(), color, DOT_SIZE));
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dotView = ((DotView) findViewById(R.id.dotsView));
        dotView.setDots(dots);

        dotView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return processOnTouchEvent(view, event);
            }
        });
    }

    /** OnTouch event handler */
    private boolean processOnTouchEvent(View view, MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            dots.addDot(new Dot(event.getX(), event.getY(), Color.MAGENTA, DOT_SIZE));
            return true;
        case MotionEvent.ACTION_MOVE:
            for(int i = 0; i < event.getHistorySize(); i++) {
                dots.addDot(new Dot(event.getHistoricalX(i), event.getHistoricalY(i), Color.CYAN,
                        (int) (event.getHistoricalSize(i) * event.getHistoricalPressure(i) * DOT_SIZE + 1)));
            }
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                generateDot(Color.RED);
                return true;
            case R.id.action_clear:
                dots.clearDots();
                return true;
        }
        return false;
    }
}
