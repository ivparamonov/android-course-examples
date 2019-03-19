package ru.ac.uniyar.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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

    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        timerTask = (TimerTask) getLastCustomNonConfigurationInstance();
        if (timerTask != null) {
            timerTask.setProgressListener(this);
        }
    }

    @OnClick(R.id.start_button)
    public void onStartButtonClick(View v) {
        int timeToCountdown = Integer.parseInt(timeEditText.getText().toString());
        timerTask = new TimerTask(timeToCountdown, this);
        timerTask.execute();
    }

    @Override
    public void onProgressUpdate(Integer remainingTime) {
        remainingTimeTextView.setText(String.valueOf(remainingTime));
    }

    @Override
    public void onDone() {
        remainingTimeTextView.setText(getString(R.string.done));
        showNotification();
    }

    private void showNotification() {
        Intent toLaunch = new Intent(this, MainActivity.class);
        toLaunch.setAction("android.intent.action.MAIN");
        toLaunch.addCategory("android.intent.category.LAUNCHER");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, toLaunch,
               PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.countdown_done))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setVibrate(new long[] { 0, 500, 500, 500, 500, 500, 500, 500 });
        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return timerTask;
    }
}
