package com.linhnt.taskmanagementservice.service;

import com.linhnt.taskmanagementservice.config.ApplicationConfigIgnoreDbTest;
import com.linhnt.taskmanagementservice.dto.exception.ApiConflictException;
import com.linhnt.taskmanagementservice.dto.request.CreateUserRequest;
import com.linhnt.taskmanagementservice.repository.TaskRepository;
import com.linhnt.taskmanagementservice.repository.UserRepository;
import com.linhnt.taskmanagementservice.service.impl.UserServiceImpl;
import com.linhnt.taskmanagementservice.utils.ParseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;

import java.util.Set;

@Import({ApplicationConfigIgnoreDbTest.class})
@MockitoBeans({
        @MockitoBean(types = UserRepository.class),
        @MockitoBean(types = TaskRepository.class)
})
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Nested
    class CreateUser {
        @Test
        void create_user_success() {
            Mockito.doReturn(false).when(userRepository).existsByUsername(Mockito.anyString());
            Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);

            CreateUserRequest request = CreateUserRequest.builder()
                    .username("user0")
                    .fullName("User 0")
                    .build();
            userService.createUser(request);
            Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(Mockito.any());
            Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        }

        @Test
        void create_user_duplicate_username() {
            Mockito.doReturn(true).when(userRepository).existsByUsername(Mockito.anyString());

            CreateUserRequest request = CreateUserRequest.builder()
                    .username("user0")
                    .fullName("User 0")
                    .build();
            ApiConflictException exception = Assertions.assertThrowsExactly(ApiConflictException.class, () -> userService.createUser(request));
            Set<String> codes = ParseUtil.parseToSetCode(exception);
            Assertions.assertTrue(codes.contains("30010001"));
            Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(Mockito.any());
        }
    }
}
