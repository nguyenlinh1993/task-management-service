package com.linhnt.taskmanagementservice.controller;

import com.linhnt.taskmanagementservice.config.ApplicationConfigIgnoreDbTest;
import com.linhnt.taskmanagementservice.dto.request.CreateUserRequest;
import com.linhnt.taskmanagementservice.repository.TaskRepository;
import com.linhnt.taskmanagementservice.repository.UserRepository;
import com.linhnt.taskmanagementservice.service.UserService;
import com.linhnt.taskmanagementservice.utils.JsonUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import({ApplicationConfigIgnoreDbTest.class})
@MockitoBeans({
        @MockitoBean(types = UserRepository.class),
        @MockitoBean(types = TaskRepository.class)
})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Nested
    class CreateUser {
        @Test
        void create_user_success() throws Exception {
            Mockito.doNothing().when(userService).createUser(Mockito.any());
            CreateUserRequest request = CreateUserRequest.builder()
                    .username("user")
                    .fullName("Aa@12345")
                    .build();
            RequestBuilder requestBuilder = post("/api/v1/users")
                    .contentType("application/json")
                    .content(JsonUtil.toJson(request));

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andReturn();
        }
    }
}
