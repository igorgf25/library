package com.igor.library.controller;

import com.igor.library.exception.EntityInvalid;
import com.igor.library.model.request.UserRequestDTO;
import com.igor.library.model.response.UserResponseDTO;
import com.igor.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createCategory(@Valid @RequestBody UserRequestDTO user, Errors errors) {

        if (errors.hasErrors()) {
            throw new EntityInvalid("Informações inválidas, por favor preencha todos os campos");
        }
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
