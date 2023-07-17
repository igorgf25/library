package com.igor.library.mockFactory;

import com.igor.library.model.Role;
import com.igor.library.model.User;
import com.igor.library.model.request.UserRequestDTO;
import com.igor.library.model.response.UserResponseDTO;

import java.util.ArrayList;

public class UserFactoryMock {
    public static final UserResponseDTO FULL_USER_RESPONSE_DTO = fullUserResponseDto();
    public static final User FULL_USER = fullUserDto();
    public static final UserRequestDTO FULL_USER_REQUEST_DTO = fullUserRequestDto();

    private static UserRequestDTO fullUserRequestDto() {
        return new UserRequestDTO("test", "12345");
    }

    private static User fullUserDto() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("12345");
        return user;
    }

    private static UserResponseDTO fullUserResponseDto() {
        return new UserResponseDTO("test");
    }

}
