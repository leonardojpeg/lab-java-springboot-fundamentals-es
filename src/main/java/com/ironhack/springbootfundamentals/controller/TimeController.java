package com.ironhack.springbootfundamentals.controller;

import com.ironhack.springbootfundamentals.dto.TimeInformation;
import com.ironhack.springbootfundamentals.service.TimeService;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the clock endpoints at the root of the application, exactly as the
 * lab specifies ({@code /time}, {@code /date}, {@code /day} and {@code /all}).
 * The {@link TimeService} arrives through constructor injection.
 */
@RestController
public class TimeController {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * {@code GET /time}
     *
     * @return the current time as plain text in {@code HH:mm:ss} format.
     */
    @GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getTime() {
        return TIME_FORMATTER.format(timeService.getCurrentTime());
    }

    /**
     * {@code GET /date}
     *
     * @return the current date as plain text in {@code yyyy-MM-dd} format.
     */
    @GetMapping(value = "/date", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getDate() {
        return DATE_FORMATTER.format(timeService.getCurrentDate());
    }

    /**
     * {@code GET /day}
     *
     * @return the current day of the week in English, for example "Monday".
     */
    @GetMapping(value = "/day", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getDayOfWeek() {
        return formatDayOfWeek(timeService.getCurrentDayOfWeek());
    }

    /**
     * {@code GET /all}
     * <p>
     * Builds the response from one single date-time reading so time, date and
     * day of the week always describe the same instant.
     *
     * @return time, date and day of the week in a single JSON document.
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeInformation getFullTimeInformation() {
        LocalDateTime now = timeService.getCurrentDateTime();
        return new TimeInformation(
                TIME_FORMATTER.format(now),
                DATE_FORMATTER.format(now),
                formatDayOfWeek(now.getDayOfWeek()));
    }

    /**
     * Renders a day of the week with its full English name, so clients read
     * "Monday" instead of the enum constant "MONDAY".
     */
    private String formatDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
