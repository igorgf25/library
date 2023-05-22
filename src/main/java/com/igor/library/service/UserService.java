package com.igor.library.service;

import com.igor.library.model.request.UserRequestDTO;
import com.igor.library.model.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO create(UserRequestDTO user);
}
