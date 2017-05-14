package org.laptech.clocker.services;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.laptech.clocker.entities.Weather;
import org.laptech.clocker.utils.WeatherDataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rlapin on 5/11/2017.
 */

public class FetchWeatherTask extends AsyncTask<String, Integer, Weather[]> {
    private static final String TAG = "FetchWeatherTask";
    private static final String REQUEST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String API_KEY = "48d96b208e0cd4201ba072e2a194634a";
    private ArrayAdapter<Weather> adapter;

    public FetchWeatherTask(ArrayAdapter<Weather> adapter) {
        this.adapter = adapter;
    }



    @Override
    protected Weather[] doInBackground(String... params) {
        StringBuilder strb = new StringBuilder();
        try {
            Uri builtUri = buildWeatherRequestUri(params[0],params[1],params[2]);
            publishProgress(10);
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(builtUri.toString()).openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

                publishProgress(20);
                String s;
                while ((s = reader.readLine()) != null) {
                    strb.append(s);
                }
                publishProgress(50);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while reading response", e.getCause());

        }
        try {
            publishProgress(90);
            return WeatherDataParser.parseWeather(strb.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error in parsing weather info", e.getCause());
            return null;
        }
    }

    private Uri buildWeatherRequestUri(String postalCode, String daysCount, String units) {
        return Uri.parse(REQUEST_URL).buildUpon()
                .appendQueryParameter("q", postalCode)
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", units)
                .appendQueryParameter("APPID", API_KEY)
                .appendQueryParameter("cnt", "" + daysCount).build();
    }

}
