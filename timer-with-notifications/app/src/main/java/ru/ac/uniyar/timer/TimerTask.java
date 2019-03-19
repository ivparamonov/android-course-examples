package ru.ac.uniyar.timer;

import android.os.AsyncTask;

public class TimerTask extends AsyncTask<Void, Integer, Void> {

    public interface ProgressListener {
        void onProgressUpdate(Integer remainingTime);
        void onDone();
    }

    private int remainingTime;
    private ProgressListener progressListener;

    public TimerTask(int timeToCountdown, ProgressListener progressListener) {
        remainingTime = timeToCountdown;
        this.progressListener = progressListener;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            do {
                publishProgress(remainingTime);
                Thread.sleep(1000);
                --remainingTime;
            } while (remainingTime != 0);
        } catch (Exception e) {
            // We should never be here
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (progressListener != null) {
            progressListener.onProgressUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (progressListener != null) {
            progressListener.onDone();
        }
    }
}
