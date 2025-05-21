package com.linhnt.taskmanagementservice.dto.exception;

import java.io.Serial;

public class ApiBadRequestException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiBadRequestException(ErrorObject errorObject) {
        super(errorObject);
    }

    public ApiBadRequestException(ErrorObject errorObject, Throwable cause) {
        super(errorObject, cause);
    }
}
