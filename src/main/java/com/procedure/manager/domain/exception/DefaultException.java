package com.procedure.manager.domain.exception;

import com.procedure.manager.domain.enumeration.ExceptionMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DefaultException extends RuntimeException {

    private final ExceptionMessage reason;
    private final HttpStatus status;
    private final String[] messageParams;
    private final String reasonText;


    public DefaultException(HttpStatus status, ExceptionMessage reason, Throwable cause, String... messageParams) {
        super(reason.getMessageKey(), cause);
        this.status = status;
        this.reason = reason;
        this.messageParams = messageParams;
        this.reasonText = null;
    }

    public DefaultException(HttpStatus status, String reason, Throwable cause) {
        super(reason, cause);
        this.status = status;
        this.reason = null;
        this.reasonText = reason;
        this.messageParams = null;
    }

    public DefaultException(HttpStatus status, ExceptionMessage reason, String... messageParams) {
        super(reason.getMessageKey());
        this.status = status;
        this.reason = reason;
        this.messageParams = messageParams;
        this.reasonText = null;
    }

    public DefaultException(ExceptionMessage reason, String... messageParams) {
        super(reason.getMessageKey());
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.reason = reason;
        this.messageParams = messageParams;
        this.reasonText = null;
    }

}