package com.linhnt.taskmanagementservice.dto.exception;

import java.io.Serial;

public class ApiForbiddenException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiForbiddenException(ErrorObject errorObject) {
        super(errorObject);
    }

    public ApiForbiddenException(ErrorObject errorObject, Throwable cause) {
        super(errorObject, cause);
    }
}
