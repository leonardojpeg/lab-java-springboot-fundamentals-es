package com.ironhack.springbootfundamentals.dto;

/**
 * Full time snapshot returned by {@code GET /all}.
 *
 * @param time      the current local time, formatted as {@code HH:mm:ss}.
 * @param date      the current local date, formatted as {@code yyyy-MM-dd}.
 * @param dayOfWeek the current day of the week in English, for example "Monday".
 */
public record TimeInformation(String time, String date, String dayOfWeek) {
}
