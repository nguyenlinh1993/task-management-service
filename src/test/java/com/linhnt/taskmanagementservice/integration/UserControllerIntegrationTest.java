package com.linhnt.taskmanagementservice.integration;

import com.linhnt.taskmanagementservice.config.TestContainersConfig;
import com.linhnt.taskmanagementservice.dto.request.CreateUserRequest;
import com.linhnt.taskmanagementservice.utils.JsonUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class CreateUser {
        @Test
        void create_user_success() throws Exception {
            CreateUserRequest request = CreateUserRequest.builder()
                    .username("user0")
                    .fullName("Aa@12345")
                    .build();
            RequestBuilder requestBuilder = post("/api/v1/users")
                    .contentType("application/json")
                    .content(JsonUtil.toJson(request));

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andReturn();
        }

        @Test
        void create_user_duplicate_username() throws Exception {
            CreateUserRequest request = CreateUserRequest.builder()
                    .username("user1")
                    .fullName("Aa@12345")
                    .build();

            RequestBuilder requestBuilder = post("/api/v1/users")
                    .contentType("application/json")
                    .content(JsonUtil.toJson(request));

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated());

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isConflict());
        }
    }
}
