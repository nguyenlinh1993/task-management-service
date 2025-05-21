package com.linhnt.taskmanagementservice.service;

import com.linhnt.taskmanagementservice.dto.core.UserDto;
import com.linhnt.taskmanagementservice.dto.request.CreateUserRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void createUser(CreateUserRequest request);

    UserDto getUser(Long id);

    List<UserDto> getUsers();

    void updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
