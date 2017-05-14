package org.laptech.clocker.entities;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author rlapin
 */
public class WeatherTest {
    @Test
    public void formatDay() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
        calendar.set(Calendar.MONTH,Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH,13);
        Weather weather = new Weather(10,10,"Cloudy",calendar.getTime());

        assertThat(weather.formatDay(),is("Fri, Jan 13"));
    }

    @Test
    public void maxMinToStr() throws Exception {

        Weather weather = new Weather(10.6,10.6,"Cloudy",new Date());

        assertThat(weather.maxMinToStr(),is("11/11"));
    }

}