package ru.ac.uniyar.counter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private TextView counterText;
    private Counter counter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null) {
            counter = new Counter();
        } else {
            counter = savedInstanceState.getParcelable("model");
        }
        counterText = (TextView) findViewById(R.id.counterText);
        findViewById(R.id.increaseButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIncreaseButtonClick(v);
            }
        });
        findViewById(R.id.resetButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetButtonClick(v);
            }
        });
        updateCounterView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("model", counter);
    }

    public void updateCounterView() {
        counterText.setText(String.valueOf(counter.getValue()));
    }

    public void onIncreaseButtonClick(View v) {
        counter.increase();
        updateCounterView();
    }

    public void onResetButtonClick(View v) {
        counter.reset();
        updateCounterView();
    }
}
