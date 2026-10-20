package com.ironhack.springbootfundamentals.exception;

import com.ironhack.springbootfundamentals.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Translates every exception thrown by the controllers into an {@link ApiError}
 * JSON body with a meaningful HTTP status code, so no request ever ends on the
 * default Whitelabel error page or a stack trace.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * A value was well formed but not acceptable, for example a blank name on
     * {@code /hello/{name}}.
     *
     * @return 400 Bad Request with the explanation carried by the exception.
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(InvalidRequestException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    /**
     * A path variable could not be converted to the type the endpoint expects,
     * for example {@code /add/abc/5}.
     *
     * @return 400 Bad Request naming the offending variable and value.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException exception,
            HttpServletRequest request) {
        String message = "'%s' is not a valid value for '%s': a whole number is required."
                .formatted(exception.getValue(), exception.getName());
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    /**
     * The arithmetic endpoints use {@code Math.addExact} and
     * {@code Math.multiplyExact}, which throw when the result does not fit in a
     * {@code long}.
     *
     * @return 400 Bad Request explaining that the operands are too large.
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ApiError> handleArithmeticOverflow(ArithmeticException exception,
            HttpServletRequest request) {
        String message = "The operands are too large: the result does not fit in a 64-bit integer.";
        logger.warn("Arithmetic error on {}: {}", request.getRequestURI(), exception.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    /**
     * No endpoint and no static resource matches the requested URL.
     *
     * @return 404 Not Found with the unmatched path.
     */
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(Exception exception, HttpServletRequest request) {
        String message = "No endpoint is mapped to '%s'.".formatted(request.getRequestURI());
        return buildResponse(HttpStatus.NOT_FOUND, message, request);
    }

    /**
     * Safety net for anything not handled above. The cause is logged on the
     * server and the client only gets a generic message.
     *
     * @return 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(Exception exception, HttpServletRequest request) {
        logger.error("Unexpected error while handling {}", request.getRequestURI(), exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request);
    }

    /**
     * Assembles the shared error payload. The content type is pinned to JSON
     * because the endpoints that fail may declare {@code text/plain}.
     */
    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        ApiError body = new ApiError(
                Instant.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI());
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
