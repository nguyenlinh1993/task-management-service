package com.linhnt.taskmanagementservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linhnt.taskmanagementservice.dto.enums.TaskStatus;
import com.linhnt.taskmanagementservice.validations.app.BugOrFeature;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@BugOrFeature
@Data
public class UpdateTaskRequest {
    @NotBlank(message = "30020001.400_task_name_is_empty")
    private String name;

    @NotNull(message = "30020002.400_user_id_is_empty")
    private Long userId;

    @NotNull(message = "30020012.400_task_status_is_empty")
    private TaskStatus status;

    @Valid
    private CreateTaskRequest.Bug bug;

    @Valid
    private CreateTaskRequest.Feature feature;

    @Data
    public static class Bug {
        @NotNull(message = "30020004.400_severity_is_empty")
        private Integer severity;

        @NotBlank(message = "30020005.400_steps_to_reproduce_is_empty")
        private String stepsToReproduce;
    }

    @Data
    public static class Feature {
        @NotBlank(message = "30020006.400_business_value_is_empty")
        private String businessValue;

        @NotNull(message = "30020007.400_deadline_is_empty")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate deadline;
    }
}
