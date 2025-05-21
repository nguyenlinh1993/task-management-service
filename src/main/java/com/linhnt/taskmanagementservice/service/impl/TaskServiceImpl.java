package com.linhnt.taskmanagementservice.service.impl;

import com.linhnt.taskmanagementservice.dto.core.TaskDto;
import com.linhnt.taskmanagementservice.dto.enums.TaskType;
import com.linhnt.taskmanagementservice.dto.exception.ApiException;
import com.linhnt.taskmanagementservice.dto.request.CreateTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.SearchTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateTaskRequest;
import com.linhnt.taskmanagementservice.entity.TaskEntity;
import com.linhnt.taskmanagementservice.entity.UserEntity;
import com.linhnt.taskmanagementservice.repository.TaskRepository;
import com.linhnt.taskmanagementservice.repository.UserRepository;
import com.linhnt.taskmanagementservice.service.TaskService;
import com.linhnt.taskmanagementservice.utils.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createTask(CreateTaskRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findByIdAndDeleteFlag(request.getUserId(), false);
        if (optionalUser.isEmpty()) {
            throw ApiException.Builder.notFound("30010002.404_user_not_found").build();
        }
        TaskEntity taskEntity = ObjectMapperUtil.map(request, TaskEntity.class);
        if (request.getBug() != null) {
            ObjectMapperUtil.map(request.getBug(), taskEntity);
        } else {
            ObjectMapperUtil.map(request.getFeature(), taskEntity);
        }
        taskEntity.setDeleteFlag(false);
        taskEntity.setUser(optionalUser.get());
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskRepository.save(taskEntity);
    }

    @Override
    public TaskDto getTask(Long id) {
        return taskRepository.findByIdAndDeleteFlag(id, false)
                .map(this::parseToTaskDto)
                .orElseThrow(() -> ApiException.Builder.notFound("30020010.404_task_not_found").build());
    }

    @Override
    public List<TaskDto> getTasks(SearchTaskRequest request) {
        return taskRepository.searchByRequest(request.getStatus(), request.getUserId(), request.getName())
                .stream()
                .map(this::parseToTaskDto)
                .collect(Collectors.toList());
    }

    private TaskDto parseToTaskDto(TaskEntity entity) {
        if (entity.getTaskType() == TaskType.BUG) {
            return TaskDto.fromBugEntity(entity);
        }
        return TaskDto.fromFeatureEntity(entity);
    }

    @Transactional
    @Override
    public void updateTask(Long id, UpdateTaskRequest request) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw ApiException.Builder.notFound("30020010.404_task_not_found").build();
        }
        TaskEntity taskEntity = taskOptional.filter(e -> Boolean.FALSE.equals(e.getDeleteFlag()))
                .orElseThrow(() -> ApiException.Builder.badRequest("30020011.400_task_is_deleted").build());

        UserEntity userEntity = request.getUserId() == null
                ? null
                : userRepository.findByIdAndDeleteFlag(request.getUserId(), false)
                .orElseThrow(() -> ApiException.Builder.notFound("30010002.404_user_not_found").build());

        ObjectMapperUtil.map(request, taskEntity);
        if (request.getBug() != null) {
            TaskEntity.resetExtendField(taskEntity);
            taskEntity.setTaskType(TaskType.BUG);
            ObjectMapperUtil.map(request.getBug(), taskEntity);
        } else if (request.getFeature() != null) {
            TaskEntity.resetExtendField(taskEntity);
            taskEntity.setTaskType(TaskType.FEATURE);
            ObjectMapperUtil.map(request.getFeature(), taskEntity);
        }

        taskEntity.setUser(userEntity);
        taskRepository.save(taskEntity);
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw ApiException.Builder.notFound("30020010.404_task_not_found").build();
        }
        TaskEntity taskEntity = taskOptional.filter(e -> Boolean.FALSE.equals(e.getDeleteFlag()))
                .orElseThrow(() -> ApiException.Builder.badRequest("30020011.400_task_is_deleted").build());
        taskEntity.setDeleteFlag(true);
        taskRepository.save(taskEntity);
    }
}
