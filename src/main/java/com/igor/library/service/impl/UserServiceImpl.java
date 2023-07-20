package com.igor.library.service.impl;

import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.User;
import com.igor.library.model.request.UserRequestDTO;
import com.igor.library.model.response.UserResponseDTO;
import com.igor.library.repository.RoleRepository;
import com.igor.library.repository.UserRepository;
import com.igor.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final static Long USER_ROLE_ID = 1L;
    private final static Long ADMIN_ROLE_ID = 2L;

    @Override
    public UserResponseDTO createUser(UserRequestDTO user) {
        log.info("UserServiceImpl.createUser inserting an user in the database");

        User response = repository.save(userConverter(user, USER_ROLE_ID));

        return mapper.map(response, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO createAdmin(UserRequestDTO user) {
        log.info("UserServiceImpl.createAdmin inserting an admin in the database");

        User response = repository.save(userConverter(user, ADMIN_ROLE_ID));

        return mapper.map(response, UserResponseDTO.class);
    }

    private User userConverter(UserRequestDTO userRequest, Long role) {
        log.info("UserServiceImpl.userConverter converting an user/admin to save in the database");

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        User user = mapper.map(userRequest, User.class);

        user.setRoles(List.of(roleRepository.findById(role).orElseThrow(() -> new EntityNotFound("Role not found"))));

        user.setPassword(bCrypt.encode(userRequest.getPassword()));

        return user;
    }
}
