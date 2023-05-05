package com.igor.library.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationFailed extends RuntimeException{
    private String message;

    public AuthenticationFailed(String message) {
        super(message);
        this.message = message;
    }
}