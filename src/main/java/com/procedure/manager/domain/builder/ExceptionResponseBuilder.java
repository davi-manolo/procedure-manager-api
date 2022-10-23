package com.procedure.manager.domain.builder;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.exception.response.ExceptionResponse;
import com.procedure.manager.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExceptionResponseBuilder {

    @Autowired
    private MessageUtils messageUtils;

    public ExceptionResponse getDatabaseExceptionResponse(DatabaseException exception) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageUtils.getMessage(exception.getReason(),exception.getMessageParams()))
                .build();
    }

//    public ExceptionResponse getInternalErrorResponse() {
//        return ExceptionResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .message(messageUtils.getMessage(ExceptionMessage.INTERNAL_SERVER_ERROR))
//                .build();
//    }
//
//    public ExceptionResponse getBadRequestResponse(String message) {
//        return ExceptionResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .message(messageUtils.getMessage(ExceptionMessage.BAD_REQUEST, message))
//                .build();
//    }
//
//    public ExceptionResponse getCustomMessageResponse(String message) {
//        return ExceptionResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .message(message)
//                .build();
//    }

}