package com.ironhack.springbootfundamentals.service;

import com.ironhack.springbootfundamentals.dto.WeatherReport;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

/**
 * Simulates a weather station. There is no external provider behind it: every
 * reading is a random value inside the range required by the lab.
 */
@Service
public class WeatherService {

    private static final int MINIMUM_TEMPERATURE_CELSIUS = -10;
    private static final int MAXIMUM_TEMPERATURE_CELSIUS = 40;
    private static final int MINIMUM_WIND_SPEED_KMH = 0;
    private static final int MAXIMUM_WIND_SPEED_KMH = 100;

    private static final List<String> WEATHER_CONDITIONS = List.of("Sunny", "Rainy", "Cloudy", "Windy");

    /**
     * @return a simulated temperature in degrees Celsius, between -10 and 40 inclusive.
     */
    public int getCurrentTemperature() {
        return randomBetweenInclusive(MINIMUM_TEMPERATURE_CELSIUS, MAXIMUM_TEMPERATURE_CELSIUS);
    }

    /**
     * @return one of "Sunny", "Rainy", "Cloudy" or "Windy", chosen at random.
     */
    public String getCurrentCondition() {
        int randomIndex = ThreadLocalRandom.current().nextInt(WEATHER_CONDITIONS.size());
        return WEATHER_CONDITIONS.get(randomIndex);
    }

    /**
     * @return a simulated wind speed in kilometres per hour, between 0 and 100 inclusive.
     */
    public int getCurrentWindSpeed() {
        return randomBetweenInclusive(MINIMUM_WIND_SPEED_KMH, MAXIMUM_WIND_SPEED_KMH);
    }

    /**
     * @return temperature, condition and wind speed gathered in a single reading.
     */
    public WeatherReport getCurrentWeatherReport() {
        return new WeatherReport(getCurrentTemperature(), getCurrentCondition(), getCurrentWindSpeed());
    }

    /**
     * Draws a pseudo-random integer in the closed interval [lowerBound, upperBound].
     * {@code ThreadLocalRandom#nextInt} excludes its upper bound, hence the +1.
     */
    private int randomBetweenInclusive(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }
}
