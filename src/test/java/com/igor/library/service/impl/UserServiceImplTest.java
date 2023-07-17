package com.igor.library.service.impl;

import com.igor.library.mockFactory.RoleFactoryMock;
import com.igor.library.mockFactory.UserFactoryMock;
import com.igor.library.model.User;
import com.igor.library.model.response.UserResponseDTO;
import com.igor.library.repository.RoleRepository;
import com.igor.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void testShoudCreateAnUser() {
        // 1. Preparation
        when(mapper.map(any(), eq(User.class))).thenReturn(UserFactoryMock.FULL_USER);
        when(roleRepository.findById(any())).thenReturn(Optional.of(RoleFactoryMock.FULL_ROLE));
        when(repository.save(any())).thenReturn(UserFactoryMock.FULL_USER);
        when(mapper.map(any(), eq(UserResponseDTO.class))).thenReturn(UserFactoryMock.FULL_USER_RESPONSE_DTO);

        // 2. Execution
        UserResponseDTO response = service.createUser(UserFactoryMock.FULL_USER_REQUEST_DTO);

        // 3. Verification
        assertEquals("test", response.getUsername(), "Must be equal");
    }

    @Test
    public void testShoudCreateAnAdmin() {
        // 1. Preparation
        when(mapper.map(any(), eq(User.class))).thenReturn(UserFactoryMock.FULL_USER);
        when(roleRepository.findById(any())).thenReturn(Optional.of(RoleFactoryMock.FULL_ROLE));
        when(repository.save(any())).thenReturn(UserFactoryMock.FULL_USER);
        when(mapper.map(any(), eq(UserResponseDTO.class))).thenReturn(UserFactoryMock.FULL_USER_RESPONSE_DTO);

        // 2. Execution
        UserResponseDTO response = service.createAdmin(UserFactoryMock.FULL_USER_REQUEST_DTO);

        // 3. Verification
        assertEquals("test", response.getUsername(), "Must be equal");
    }

}