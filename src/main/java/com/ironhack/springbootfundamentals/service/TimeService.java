package com.ironhack.springbootfundamentals.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.stereotype.Service;

/**
 * Reads the current time from the system clock using {@code java.time}.
 * The service returns {@code java.time} types and leaves formatting to the
 * controller, which is the layer responsible for presentation.
 */
@Service
public class TimeService {

    /**
     * @return the current local time.
     */
    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /**
     * @return the current local date.
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * @return the current day of the week.
     */
    public DayOfWeek getCurrentDayOfWeek() {
        return LocalDate.now().getDayOfWeek();
    }

    /**
     * Single reading of date and time together. The combined endpoint uses this
     * instead of three separate calls so the values cannot disagree when the
     * request happens to cross midnight.
     *
     * @return the current local date and time.
     */
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
