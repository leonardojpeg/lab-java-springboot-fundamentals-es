package com.ironhack.springbootfundamentals.controller;

import com.ironhack.springbootfundamentals.dto.WeatherReport;
import com.ironhack.springbootfundamentals.service.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the simulated weather station under {@code /weather}. The
 * {@link WeatherService} arrives through constructor injection.
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * {@code GET /weather/temperature}
     *
     * @return the current temperature in degrees Celsius, as plain text, for example "12 C".
     */
    @GetMapping(value = "/temperature", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getTemperature() {
        return weatherService.getCurrentTemperature() + " C";
    }

    /**
     * {@code GET /weather/condition}
     *
     * @return the current sky condition as plain text, for example "Sunny".
     */
    @GetMapping(value = "/condition", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getCondition() {
        return weatherService.getCurrentCondition();
    }

    /**
     * {@code GET /weather/wind}
     *
     * @return the current wind speed in km/h, as plain text, for example "37 km/h".
     */
    @GetMapping(value = "/wind", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getWindSpeed() {
        return weatherService.getCurrentWindSpeed() + " km/h";
    }

    /**
     * {@code GET /weather/all}
     *
     * @return temperature, condition and wind speed in a single JSON document.
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherReport getFullWeatherReport() {
        return weatherService.getCurrentWeatherReport();
    }
}
