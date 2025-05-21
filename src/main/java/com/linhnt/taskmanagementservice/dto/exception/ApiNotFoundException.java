package com.linhnt.taskmanagementservice.dto.exception;

import java.io.Serial;

public class ApiNotFoundException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiNotFoundException(ErrorObject errorObject) {
        super(errorObject);
    }

    public ApiNotFoundException(ErrorObject errorObject, Throwable cause) {
        super(errorObject, cause);
    }
}
