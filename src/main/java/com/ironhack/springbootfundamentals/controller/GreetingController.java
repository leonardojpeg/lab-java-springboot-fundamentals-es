package com.ironhack.springbootfundamentals.controller;

import com.ironhack.springbootfundamentals.exception.InvalidRequestException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Greeting and arithmetic endpoints. Every response is plain text so it can be
 * read straight from a browser address bar.
 */
@RestController
public class GreetingController {

    /**
     * {@code GET /hello}
     *
     * @return the fixed greeting "Hello World!".
     */
    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello() {
        return "Hello World!";
    }

    /**
     * {@code GET /hello/{name}}
     *
     * @param name the name to greet.
     * @return the personalised greeting "Hello {name}!".
     * @throws InvalidRequestException if the name is empty or only whitespace,
     *                                 which is reported as 400 Bad Request.
     */
    @GetMapping(value = "/hello/{name}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHelloTo(@PathVariable String name) {
        if (name.isBlank()) {
            throw new InvalidRequestException("The name must not be blank.");
        }
        return "Hello " + name.strip() + "!";
    }

    /**
     * {@code GET /add/{num1}/{num2}}
     * <p>
     * The operands are bound as {@code long} rather than {@code int} to cover
     * the widest integral range, and the addition goes through
     * {@link Math#addExact(long, long)} so a result that does not fit fails
     * loudly with 400 Bad Request instead of silently wrapping around.
     *
     * @param num1 the first operand.
     * @param num2 the second operand.
     * @return the sum of both operands.
     */
    @GetMapping(value = "/add/{num1}/{num2}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String add(@PathVariable long num1, @PathVariable long num2) {
        return String.valueOf(Math.addExact(num1, num2));
    }

    /**
     * {@code GET /multiply/{num1}/{num2}}
     * <p>
     * Same overflow policy as {@link #add(long, long)}: the product is computed
     * with {@link Math#multiplyExact(long, long)} so an out-of-range result is
     * rejected instead of returning a wrong number.
     *
     * @param num1 the first operand.
     * @param num2 the second operand.
     * @return the product of both operands.
     */
    @GetMapping(value = "/multiply/{num1}/{num2}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String multiply(@PathVariable long num1, @PathVariable long num2) {
        return String.valueOf(Math.multiplyExact(num1, num2));
    }
}
