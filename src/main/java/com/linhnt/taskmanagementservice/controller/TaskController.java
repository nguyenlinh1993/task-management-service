package com.linhnt.taskmanagementservice.controller;

import com.linhnt.taskmanagementservice.dto.core.TaskDto;
import com.linhnt.taskmanagementservice.dto.request.CreateTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.SearchTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateTaskRequest;
import com.linhnt.taskmanagementservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(name = "T0001")
    public void createTask(@Valid @RequestBody CreateTaskRequest request) {
        taskService.createTask(request);
    }

    @GetMapping(name = "T0002")
    public List<TaskDto> getTasks(SearchTaskRequest request) {
        return taskService.getTasks(request);
    }

    @GetMapping(path = "/{id}", name = "T0003")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{id}", name = "T0004")
    public void updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
        taskService.updateTask(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", name = "T0005")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
