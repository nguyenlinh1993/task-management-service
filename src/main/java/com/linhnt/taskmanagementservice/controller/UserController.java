package com.linhnt.taskmanagementservice.controller;

import com.linhnt.taskmanagementservice.dto.core.UserDto;
import com.linhnt.taskmanagementservice.dto.request.CreateUserRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateUserRequest;
import com.linhnt.taskmanagementservice.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(name = "U0001")
    public void createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
    }

    @GetMapping(name = "U0002")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/{id}", name = "U0003")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{id}", name = "U0004")
    public void updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", name = "U0005")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
