package com.ironhack.springbootfundamentals.dto;

/**
 * Full weather snapshot returned by {@code GET /weather/all}. The units are
 * part of the field names so the JSON is self-describing.
 *
 * @param temperatureCelsius the simulated temperature in degrees Celsius.
 * @param condition          the simulated sky condition.
 * @param windSpeedKmh       the simulated wind speed in kilometres per hour.
 */
public record WeatherReport(int temperatureCelsius, String condition, int windSpeedKmh) {
}
