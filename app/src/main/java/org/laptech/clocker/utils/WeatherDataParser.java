package org.laptech.clocker.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.laptech.clocker.entities.Weather;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * Parse weather information json
 * @author rlapin
 */

public class WeatherDataParser {

    private static final String LIST_NODE = "list";
    private static final String TEMP_NODE = "temp";
    private static final String MAX_NODE = "max";
    private static final String MIN_NODE = "min";
    private static final String MAIN_NODE = "main";
    private static final String WEATHER_NODE = "weather";

    public static Weather[] parseWeather(String weatherJsonStr) throws JSONException {
        JSONObject weather = new JSONObject(weatherJsonStr);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
        gregorianCalendar.setTime(new Date());

        JSONArray daysInfo = weather.getJSONArray(LIST_NODE);
        Weather[] weatherInfo = new Weather[daysInfo.length()];
        for (int i = 0; i < daysInfo.length(); i++) {
            JSONObject dayInfo = daysInfo.getJSONObject(i);
            JSONObject tempInfo = dayInfo.getJSONObject(TEMP_NODE);
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            weatherInfo[i] = new Weather(tempInfo.getDouble(MAX_NODE), tempInfo.getDouble(MIN_NODE), dayInfo.getJSONArray(WEATHER_NODE).getJSONObject(0).getString(MAIN_NODE), gregorianCalendar.getTime());
        }
        return weatherInfo;
    }
}
