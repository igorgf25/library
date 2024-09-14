package com.igor.library.controller;

import com.igor.library.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final JwtUtil;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return
    }

}
