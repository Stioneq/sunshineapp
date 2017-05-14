package org.laptech.clocker.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Weather for the given day
 * @author rlapin
 */

public class Weather {
    private double max;
    private double min;
    private String description;
    private Date day;

    public Weather(double max, double min, String description, Date day) {
        this.max = max;
        this.min = min;
        this.description = description;
        this.day = day;
    }

    @Override
    public String toString() {
        return formatDay() + " - " + description + " - " + maxMinToStr();
    }

    protected String formatDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d", Locale.ENGLISH);
        return dateFormat.format(day);
    }

    protected String maxMinToStr() {
        return Math.round(max) + "/" + Math.round(min);
    }
}
