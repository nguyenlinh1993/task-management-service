package com.linhnt.taskmanagementservice.dto.exception;

import java.io.Serial;

public class ApiConflictException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiConflictException(ErrorObject errorObject) {
        super(errorObject);
    }

    public ApiConflictException(ErrorObject errorObject, Throwable cause) {
        super(errorObject, cause);
    }
}
