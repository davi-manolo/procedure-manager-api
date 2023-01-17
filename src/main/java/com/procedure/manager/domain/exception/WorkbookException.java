package com.procedure.manager.domain.exception;

import com.procedure.manager.domain.enumeration.ExceptionMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WorkbookException extends DefaultException {

    public WorkbookException(ExceptionMessage reson) {
        super(reson);
    }

    public WorkbookException(HttpStatus status, ExceptionMessage reson) {
        super(status, reson);
    }

}
