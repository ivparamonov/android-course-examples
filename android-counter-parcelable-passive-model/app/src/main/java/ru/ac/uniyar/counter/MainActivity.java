package ru.ac.uniyar.counter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity
{
    @BindView(R.id.counterText) TextView counterText;
    private Counter counter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            counter = new Counter();
        } else {
            counter = savedInstanceState.getParcelable("model");
        }
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

    @OnClick(R.id.increaseButton)
    public void onIncreaseButtonClick(View v) {
        counter.increase();
        updateCounterView();
    }

    @OnClick(R.id.resetButton)
    public void onResetButtonClick(View v) {
        counter.reset();
        updateCounterView();
    }
}
