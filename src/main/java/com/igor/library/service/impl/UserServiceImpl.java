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
    private final static Long ADMIN_ROLE_ID = 2L;

    @Override
    public UserResponseDTO createUser(UserRequestDTO user) {

        User response = repository.save(userConverter(user, USER_ROLE_ID));

        return mapper.map(response, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO createAdmin(UserRequestDTO user) {

        User response = repository.save(userConverter(user, ADMIN_ROLE_ID));

        return mapper.map(response, UserResponseDTO.class);
    }

    public User userConverter(UserRequestDTO userRequest, Long role) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        User user = mapper.map(userRequest, User.class);

        user.setRoles(List.of(roleRepository.findById(role).orElseThrow(() -> new EntityNotFound("Role não encontrado"))));

        user.setPassword(bCrypt.encode(userRequest.getPassword()));

        return user;
    }
}
