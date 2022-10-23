package com.procedure.manager.domain.exception.handler;

import com.procedure.manager.domain.builder.ExceptionResponseBuilder;
import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.exception.response.ExceptionResponse;
import com.procedure.manager.util.ResponseEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionResponseBuilder responseBuilder;

    public GlobalExceptionHandler(ExceptionResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExists(DatabaseException exception) {
        ExceptionResponse exceptionResponse = responseBuilder.getDatabaseExceptionResponse(exception);
        log.error(exceptionResponse.getMessage(), exception);
        return ResponseEntityUtils.defaultResponse(exception.getStatus(), exceptionResponse);
    }

}
