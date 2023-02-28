package com.igor.library.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityInvalid extends RuntimeException{
    private String message;

    public EntityInvalid(String message) {
        super(message);
        this.message = message;
    }
}