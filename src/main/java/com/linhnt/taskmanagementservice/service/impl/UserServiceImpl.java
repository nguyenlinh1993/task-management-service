package com.linhnt.taskmanagementservice.service.impl;

import com.linhnt.taskmanagementservice.dto.core.UserDto;
import com.linhnt.taskmanagementservice.dto.exception.ApiException;
import com.linhnt.taskmanagementservice.dto.request.CreateUserRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateUserRequest;
import com.linhnt.taskmanagementservice.entity.UserEntity;
import com.linhnt.taskmanagementservice.repository.UserRepository;
import com.linhnt.taskmanagementservice.service.UserService;
import com.linhnt.taskmanagementservice.utils.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw ApiException.Builder.conflict("30010001.409_username_existed").build();
        }
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .deleteFlag(false)
                .build();
        userRepository.save(userEntity);
    }

    @Override
    public UserDto getUser(Long id) {
        return userRepository.findByIdAndDeleteFlag(id, false)
                .map(e -> ObjectMapperUtil.map(e, UserDto.class))
                .orElseThrow(() -> ApiException.Builder.notFound("30010002.404_user_not_found").build());
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAllByDeleteFlag(false)
                .stream().map(e -> ObjectMapperUtil.map(e, UserDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateUser(Long id, UpdateUserRequest request) {
        Optional<UserEntity> userOp = userRepository.findById(id);
        if (userOp.isEmpty()) {
            throw ApiException.Builder.notFound("30010002.404_user_not_found").build();
        }
        UserEntity userEntity = userOp.filter(e -> Boolean.FALSE.equals(e.getDeleteFlag()))
                .orElseThrow(() -> ApiException.Builder.badRequest("30010005.400_user_is_deleted").build());
        userEntity.setFullName(request.getFullName());
        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> userOp = userRepository.findById(id);
        if (userOp.isEmpty()) {
            throw ApiException.Builder.notFound("30010002.404_user_not_found").build();
        }
        UserEntity userEntity = userOp.filter(e -> Boolean.FALSE.equals(e.getDeleteFlag()))
                .orElseThrow(() -> ApiException.Builder.badRequest("30010005.400_user_is_deleted").build());
        userEntity.setDeleteFlag(true);
        userRepository.save(userEntity);
    }
}
