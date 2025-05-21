package com.linhnt.taskmanagementservice.service;

import com.linhnt.taskmanagementservice.dto.core.TaskDto;
import com.linhnt.taskmanagementservice.dto.request.CreateTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.SearchTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateTaskRequest;

import java.util.List;

public interface TaskService {
    void createTask(CreateTaskRequest request);

    TaskDto getTask(Long id);

    List<TaskDto> getTasks(SearchTaskRequest request);

    void updateTask(Long id, UpdateTaskRequest request);

    void deleteTask(Long id);
}
