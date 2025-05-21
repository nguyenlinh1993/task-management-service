package com.linhnt.taskmanagementservice.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String errorCode;

    private String errorMessage;

    private String errorType;
}
