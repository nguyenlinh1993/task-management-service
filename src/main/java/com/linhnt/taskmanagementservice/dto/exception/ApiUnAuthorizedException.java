package com.linhnt.taskmanagementservice.dto.exception;

import java.io.Serial;

public class ApiUnAuthorizedException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiUnAuthorizedException(ErrorObject errorObject) {
        super(errorObject);
    }

    public ApiUnAuthorizedException(ErrorObject errorObject, Throwable cause) {
        super(errorObject, cause);
    }
}
