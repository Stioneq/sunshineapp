package org.laptech.clocker.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.laptech.clocker.R;

public class DetailedWeatherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DetailedWeatherFragment()).commit();
    }

    public static class DetailedWeatherFragment extends Fragment {

        public static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private String mForecast;
        private static final String TAG = "DetailedWeatherFragment";
        public DetailedWeatherFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View rootView = inflater.inflate(R.layout.detailed_weather_view,container,false);

            mForecast = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            TextView viewById = (TextView) rootView.findViewById(R.id.detailed_weather_text_view);
            viewById.setText(mForecast);
            return rootView;

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.settings_menu_item) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
        private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecast + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detail, menu);
            MenuItem menuItem = menu.findItem(R.id.share_menu_item);
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if(shareActionProvider!=null){
                shareActionProvider.setShareIntent(createShareForecastIntent());
            }else{
                Log.d(TAG, "Share action provider is null?");
            }
        }
    }
}
