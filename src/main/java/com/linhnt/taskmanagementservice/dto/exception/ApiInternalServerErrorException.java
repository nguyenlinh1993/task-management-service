package com.linhnt.taskmanagementservice.dto.exception;

import java.io.Serial;

public class ApiInternalServerErrorException extends ApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiInternalServerErrorException(ErrorObject errorObject) {
        super(errorObject);
    }

    public ApiInternalServerErrorException(ErrorObject errorObject, Throwable cause) {
        super(errorObject, cause);
    }
}
