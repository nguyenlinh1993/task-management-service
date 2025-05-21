package com.linhnt.taskmanagementservice.dto.request;

import com.linhnt.taskmanagementservice.dto.enums.TaskStatus;
import lombok.Data;

@Data
public class SearchTaskRequest {
    private TaskStatus status;
    private Long userId;
    private String name;
}
