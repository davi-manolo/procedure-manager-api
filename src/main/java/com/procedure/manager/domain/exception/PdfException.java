package com.procedure.manager.domain.exception;

import com.procedure.manager.domain.enumeration.ExceptionMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PdfException extends DefaultException {

    public PdfException(ExceptionMessage reson) {
        super(reson);
    }

    public PdfException(HttpStatus status, ExceptionMessage reson) {
        super(status, reson);
    }

}
