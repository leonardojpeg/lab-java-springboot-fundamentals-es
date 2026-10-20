package com.ironhack.springbootfundamentals.dto;

/**
 * Body returned for every failed request, so clients always get the same
 * machine-readable shape instead of an HTML error page.
 *
 * @param timestamp when the error was produced, as an ISO-8601 instant.
 * @param status    the HTTP status code (for example 400).
 * @param error     the HTTP status reason phrase (for example "Bad Request").
 * @param message   a human-readable explanation of what went wrong.
 * @param path      the request URI that failed.
 */
public record ApiError(String timestamp, int status, String error, String message, String path) {
}
