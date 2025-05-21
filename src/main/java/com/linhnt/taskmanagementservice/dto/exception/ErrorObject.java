package com.linhnt.taskmanagementservice.dto.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linhnt.taskmanagementservice.utils.MessageUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class ErrorObject implements Serializable {
    private static final int LENGTH_KEY_MESSAGE = 2;

    @Serial
    private static final long serialVersionUID = 1L;

    private transient Set<ErrorDetail> errors;

    private Date errorTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient Object debugInfo;

    public ErrorObject() {
        this.errors = new HashSet<>();
        this.errorTime = new Date();
    }

    public void addError(String errorCode, String... params) {
        addError(errorCode, null, params);
    }

    public void addError(String errorCode, ErrorType errorType, String... params) {
        if (errorCode == null) {
            return;
        }

        ErrorDetail errorDetail = new ErrorDetail();
        String[] arr = errorCode.split("\\.");
        String code = arr[0];
        errorDetail.setErrorCode(code);

        if (errorType != null) {
            errorDetail.setErrorType(errorType.name());
        }

        if (arr.length == LENGTH_KEY_MESSAGE) { // Case error type is Internal
            errorDetail.setErrorType(ErrorType.INTERNAL.name());
            errorDetail.setErrorMessage(params != null && params.length > 0
                    ? MessageUtil.getMessage(errorCode, params)
                    : MessageUtil.getMessage(errorCode));
        } else if (errorType == ErrorType.EXTERNAL) { // Case error type is External
            errorDetail.setErrorType(errorType.name());
            errorDetail.setErrorMessage(params[0] != null ? params[0] : "");
        } else {
            errorDetail.setErrorType(null);
        }

        this.errors.add(errorDetail);
    }
}
