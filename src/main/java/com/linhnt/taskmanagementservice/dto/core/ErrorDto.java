package com.linhnt.taskmanagementservice.dto.core;

import com.linhnt.taskmanagementservice.dto.exception.ErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private boolean hasError;
    private Set<ErrorDetail> errors;
    private Object debugInfo;
    private long timestamp;
}
