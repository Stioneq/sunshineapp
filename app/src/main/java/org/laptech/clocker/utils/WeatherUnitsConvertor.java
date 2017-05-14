package org.laptech.clocker.utils;

/**
 * Convert values from celsius units to fahrenheits and visa versa
 * @author rlapin
 */

public class WeatherUnitsConvertor {
    public static double toImperic(double metricVal){
        return metricVal * 9 / 5 + 32;
    }
    public static double toMetric(double impericVal){
        return (impericVal - 32) * 5/9;
    }
}
