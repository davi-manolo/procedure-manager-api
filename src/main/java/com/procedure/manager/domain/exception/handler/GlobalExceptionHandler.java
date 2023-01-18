package com.procedure.manager.domain.exception.handler;

import com.procedure.manager.domain.builder.ExceptionResponseBuilder;
import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.exception.response.ExceptionResponse;
import com.procedure.manager.util.ResponseEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Slf4j
@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    private final ExceptionResponseBuilder responseBuilder;

    public GlobalExceptionHandler(ExceptionResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionResponse> handleDatabase(DatabaseException exception) {
        ExceptionResponse exceptionResponse = responseBuilder.getDatabaseExceptionResponse(exception);
        log.error(exceptionResponse.getMessage(), exception);
        return ResponseEntityUtils.defaultResponse(exception.getStatus(), exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException exception) {
        ExceptionResponse exceptionResponse = responseBuilder.getValidationException(exception);
        log.error(exceptionResponse.getMessage(), exception);
        return ResponseEntityUtils.defaultResponse(BAD_REQUEST, exceptionResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleMissingParameter(MissingServletRequestParameterException exception) {
        ExceptionResponse exceptionResponse = responseBuilder.getMissingParameterException(exception);
        log.error(exceptionResponse.getMessage(), exception);
        return ResponseEntityUtils.defaultResponse(BAD_REQUEST, exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationParameter(ConstraintViolationException exception) {
        ExceptionResponse exceptionResponse = responseBuilder.getValidationParameterException(exception);
        log.error(exceptionResponse.getMessage(), exception);
        return ResponseEntityUtils.defaultResponse(BAD_REQUEST, exceptionResponse);
    }

}
