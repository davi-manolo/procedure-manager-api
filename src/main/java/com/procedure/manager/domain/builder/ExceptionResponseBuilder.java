package com.procedure.manager.domain.builder;

import com.procedure.manager.domain.enumeration.ExceptionMessage;
import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.exception.response.ExceptionResponse;
import com.procedure.manager.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ExceptionResponseBuilder {

    @Autowired
    private MessageUtils messageUtils;

    public ExceptionResponse getDatabaseExceptionResponse(DatabaseException exception) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageUtils.getMessage(exception.getReason(), exception.getMessageParams()))
                .build();
    }

    public ExceptionResponse getValidationException(MethodArgumentNotValidException exception) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageUtils.getMessage(ExceptionMessage.VALIDATION_REQUEST, exception.getLocalizedMessage()))
                .errorMessageList(exception.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()))
                .build();
    }

    public ExceptionResponse getMissingParameterException(MissingServletRequestParameterException exception) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageUtils.getMessage(ExceptionMessage.MISSING_PARAMETER_REQUEST, exception.getParameterName())
                        .concat(" '" + exception.getParameterName() + "'"))
                .build();
    }

    public ExceptionResponse getValidationParameterException(ConstraintViolationException exception) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageUtils.getMessage(ExceptionMessage.INVALID_PARAMETER_REQUEST, exception.getLocalizedMessage())
                        .concat(" " + exception.getConstraintViolations().stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining()))
                )
                .build();
    }

}