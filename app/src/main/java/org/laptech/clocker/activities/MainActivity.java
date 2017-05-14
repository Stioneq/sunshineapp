package org.laptech.clocker.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.laptech.clocker.R;
import org.laptech.clocker.entities.Weather;
import org.laptech.clocker.services.FetchWeatherTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter<Weather> arrayAdapter;
    private ListView listView;
    private TextView progressTextView;


    @Override
    protected void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main);


        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_forecast_item, R.id.list_forecast_item);
        listView = (ListView) this.findViewById(R.id.list_forecast_view);
        listView.setAdapter(arrayAdapter);
        progressTextView = (TextView) findViewById(R.id.forecast_progress_text);
        initListeners();
    }

    private void initListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, DetailedWeatherActivity.class).putExtra(Intent.EXTRA_TEXT, parent.getItemAtPosition(position).toString()));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_menu_item) {
            updateWeather();
            return true;
        } else if (item.getItemId() == R.id.settings_menu_item) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

            return true;
        } else if (item.getItemId() == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        String location = sharedPreferences
                .getString(getString(R.string.pref_location_key), getString(R.string.pref_default_location));
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Log.d(TAG, "openPreferredLocationInMap: Couldn't call " + location + ". No app found");
        }
    }

    private void updateWeather() {
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(arrayAdapter) {
            @Override
            protected void onPostExecute(Weather[] weathers) {
                progressTextView.setVisibility(View.GONE);
                if (weathers != null) {
                    listView.setVisibility(View.VISIBLE);
                    arrayAdapter.clear();
                    arrayAdapter.addAll(weathers);
                }
            }

            @Override
            protected void onPreExecute() {
                progressTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                progressTextView.setText(String.format(getResources().getString(R.string.progressText), values[0]));
            }
        };
        SharedPreferences defaultSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        fetchWeatherTask.execute(
                defaultSharedPreferences
                        .getString(getString(R.string.pref_location_key), getString(R.string.pref_default_location)),
                "10",
                defaultSharedPreferences.getString(getString(R.string.pref_units_key), getString(R.string.pref_weather_units_default)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
