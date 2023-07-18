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
@RequestMapping()
public class UserController {

    private final UserService userService;

    @PostMapping("user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user, Errors errors) {

        if (errors.hasErrors()) {
            throw new EntityInvalid("Invalid information, please fill in all fields");
        }
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("admin")
    public ResponseEntity<UserResponseDTO> createAdmin(@Valid @RequestBody UserRequestDTO user, Errors errors) {

        if (errors.hasErrors()) {
            throw new EntityInvalid("Invalid information, please fill in all fields");
        }
        return new ResponseEntity<>(userService.createAdmin(user), HttpStatus.CREATED);
    }
}
