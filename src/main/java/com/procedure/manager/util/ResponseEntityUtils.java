package com.procedure.manager.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.*;

@SuppressWarnings("unused")
@Component
public final class ResponseEntityUtils {

    private ResponseEntityUtils() {}

    public static <T> ResponseEntity<T> defaultResponse(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.status(OK).body(body);
    }

    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(CREATED).body(body);
    }

    public static <T> ResponseEntity<T> updated(T body) {
        return ResponseEntity.status(ACCEPTED).body(body);
    }

    public static <T> ResponseEntity<T> notFound(T body) {
        return ResponseEntity.status(NOT_FOUND).body(body);
    }

    public static <T> ResponseEntity<T> notAcceptable(T body) {
        return ResponseEntity.status(NOT_ACCEPTABLE).body(body);
    }

    public static <T> ResponseEntity<T> conflict(T body) {
        return ResponseEntity.status(CONFLICT).body(body);
    }

}