package com.igor.library.service.impl;

import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.User;
import com.igor.library.model.request.UserRequestDTO;
import com.igor.library.model.response.UserResponseDTO;
import com.igor.library.repository.RoleRepository;
import com.igor.library.repository.UserRepository;
import com.igor.library.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final static Long USER_ROLE_ID = 1L;

    @Override
    public UserResponseDTO create(UserRequestDTO user) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        User userRequest = mapper.map(user, User.class);

        userRequest.setRoles(List.of(roleRepository.findById(USER_ROLE_ID).orElseThrow(() -> new EntityNotFound("Role não encontrado"))));

        userRequest.setPassword(bCrypt.encode(user.getPassword()));

        repository.save(userRequest);

        return mapper.map(userRequest, UserResponseDTO.class);
    }
}
