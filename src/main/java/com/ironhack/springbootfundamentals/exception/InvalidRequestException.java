package com.ironhack.springbootfundamentals.exception;

/**
 * Raised when a request carries a syntactically valid but semantically
 * unacceptable value, such as a blank name on {@code /hello/{name}}.
 * {@link GlobalExceptionHandler} maps it to 400 Bad Request.
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
