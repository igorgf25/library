package com.igor.library.controller;

import com.igor.library.exception.CustomRestExceptionHandler;
import com.igor.library.mockFactory.UserFactoryMock;
import com.igor.library.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @Test
    public void postUserShouldCreateAnUser() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("username", "test");
        json.put("password", "12345");

        when(userService.createUser(any())).thenReturn(UserFactoryMock.FULL_USER_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());


    }

    @Test
    public void postUserShouldThrowAnError() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("username", "test");

        when(userService.createUser(any())).thenReturn(UserFactoryMock.FULL_USER_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void postAdminShouldCreateAnAdmin() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("username", "test");
        json.put("password", "12345");

        when(userService.createUser(any())).thenReturn(UserFactoryMock.FULL_USER_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated());


    }

    @Test
    public void postAdminShouldThrowAnError() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("username", "test");

        when(userService.createUser(any())).thenReturn(UserFactoryMock.FULL_USER_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isBadRequest());


    }
}