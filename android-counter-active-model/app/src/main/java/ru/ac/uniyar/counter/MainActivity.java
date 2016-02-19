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
    private Counter counter = new Counter();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        counter.setOnModificationListener(new Counter.OnModificationListener() {
            @Override
            public void onModification(Counter sender) {
                counterText.setText(String.valueOf(counter.getValue()));
            }
        });
    }

    public void onIncreaseButtonClick(View v) {
        counter.increase();
    }

    public void onResetButtonClick(View v) {
        counter.reset();
    }
}
