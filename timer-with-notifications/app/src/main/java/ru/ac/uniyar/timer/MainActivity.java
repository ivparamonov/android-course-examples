package ru.ac.uniyar.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements TimerTask.ProgressListener {
    @BindView(R.id.remaining_time_text_view)
    TextView remainingTimeTextView;

    @BindView(R.id.time_edit_text)
    EditText timeEditText;

    @BindView(R.id.start_button)
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_button)
    public void onStartButtonClick(View v) {
        int timeToCountdown = Integer.parseInt(timeEditText.getText().toString());
        new TimerTask(timeToCountdown, this).execute();
    }

    @Override
    public void onProgressUpdate(Integer remainingTime) {
        remainingTimeTextView.setText(String.valueOf(remainingTime));
    }

    @Override
    public void onDone() {
        remainingTimeTextView.setText(getString(R.string.done));
    }
}
