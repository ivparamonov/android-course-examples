package ru.ac.uniyar.ipdeterminator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ip_text_view) TextView ipTextView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.determine_button)
    public void onDetermineButtonClick(View v) {
        new IPDeterminationTask().execute();
    }

    private class IPDeterminationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://api.ipify.org/?format=json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String ip = (String) new JSONObject(reader.readLine()).get("ip");
                reader.close();
                return ip;
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            ipTextView.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String ip) {
            progressBar.setVisibility(View.INVISIBLE);
            ipTextView.setText(ip);
        }
    }
}
