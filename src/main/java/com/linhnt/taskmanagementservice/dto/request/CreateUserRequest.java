package com.linhnt.taskmanagementservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@NotNull
@Data
public class CreateUserRequest {
    @NotBlank(message = "30010003.400_username_is_empty")
    private String username;

    @NotBlank(message = "30010004.400_full_name_is_empty")
    private String fullName;
}
