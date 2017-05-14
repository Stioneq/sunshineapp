package org.laptech.clocker.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author rlapin
 */
public class WeatherUnitsConvertorTest {
    @Test
    public void toImperic() throws Exception {
        assertEquals(WeatherUnitsConvertor.toImperic(0), 32,1E-4);
        assertEquals(WeatherUnitsConvertor.toImperic(225), 437,1E-4);
    }




    @Test
    public void toMetric() throws Exception {
        assertEquals(WeatherUnitsConvertor.toMetric(-40), -40,1E-4);
        assertEquals(WeatherUnitsConvertor.toMetric(410), 210,1E-4);
    }

}