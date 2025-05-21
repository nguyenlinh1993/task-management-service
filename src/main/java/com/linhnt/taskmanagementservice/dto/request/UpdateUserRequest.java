package com.linhnt.taskmanagementservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "30010004.400_full_name_is_empty")
    private String fullName;
}
