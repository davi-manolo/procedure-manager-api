package com.procedure.manager.domain.enumeration;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    DATABASE_USER_ALREADY_EXISTS("error.database.user-already-exists"),
    DATABASE_USER_DOES_NOT_EXIST("error.database.user-does-not-exist"),
    DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS("error.database.procedure-type-already-exists"),
    DATABASE_PROCEDURE_TYPE_DOES_NOT_EXIST("error.database.procedure-type-does-not-exist"),
    DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST("error.database.procedure-type-list-does-not-exist"),
    DATABASE_PROCEDURE_DOES_NOT_EXIST("error.database.procedure-does-not-exist"),
    DATABASE_PROCEDURE_LIST_DOES_NOT_EXIST("error.database.procedure-list-does-not-exist"),
    VALIDATION_REQUEST("error.validation"),
    MISSING_PARAMETER_REQUEST("missing.parameter.request"),
    INVALID_PARAMETER_REQUEST("invalid.parameter.request"),
    WORKBOOK_IO_ERROR("workbook.io.error"),
    WORKBOOK_CONVERT_TO_ARRAY_ERROR("workbook.array.error"),
    ERROR_ON_LOGIN("error.process.login"),
    NOT_AUTHORIZED("not.authorized");

    private final String messageKey;

    ExceptionMessage(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String toString() {
        return messageKey;
    }

}
