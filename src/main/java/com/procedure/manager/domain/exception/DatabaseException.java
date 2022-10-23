package com.procedure.manager.domain.exception;

import com.procedure.manager.domain.enumeration.ExceptionMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DatabaseException extends DefaultException {

    public DatabaseException(ExceptionMessage reson) {
        super(reson);
    }

    public DatabaseException(HttpStatus status, ExceptionMessage reson) {
        super(status, reson);
    }

}
