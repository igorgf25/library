package com.igor.library.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFound extends RuntimeException{
    private String message;

    public EntityNotFound(String message) {
        super(message);
        this.message = message;
    }
}