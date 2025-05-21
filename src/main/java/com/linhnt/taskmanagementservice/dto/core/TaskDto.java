package com.linhnt.taskmanagementservice.dto.core;

import com.linhnt.taskmanagementservice.dto.enums.TaskStatus;
import com.linhnt.taskmanagementservice.entity.TaskEntity;
import com.linhnt.taskmanagementservice.utils.ObjectMapperUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;

    private String name;

    private TaskStatus status;

    private LocalDateTime createdAt;

    private Boolean deleteFlag;

    private UserDto user;

    private String taskType;

    private Bug bug;

    private Feature feature;

    @Data
    public static class Bug {
        private Integer severity;
        private String stepsToReproduce;
    }

    @Data
    public static class Feature {
        private String businessValue;
        private LocalDate deadline;
    }

    public static TaskDto fromBugEntity(TaskEntity e) {
        TaskDto taskDto = ObjectMapperUtil.map(e, TaskDto.class);
        taskDto.taskType = "BUG";
        taskDto.bug = ObjectMapperUtil.map(e, Bug.class);
        return taskDto;
    }

    public static TaskDto fromFeatureEntity(TaskEntity e) {
        TaskDto taskDto = ObjectMapperUtil.map(e, TaskDto.class);
        taskDto.taskType = "FEATURE";
        taskDto.feature = ObjectMapperUtil.map(e, Feature.class);
        return taskDto;
    }
}
