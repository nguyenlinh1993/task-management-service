package com.linhnt.taskmanagementservice.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarningDetail {
    private String warningCode;

    private String warningMessage;

    private String warningType;
}
