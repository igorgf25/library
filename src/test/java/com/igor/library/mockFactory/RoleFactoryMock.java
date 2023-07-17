package com.igor.library.mockFactory;

import com.igor.library.model.Role;

import java.util.Optional;

public class RoleFactoryMock {
    public static final Role FULL_ROLE = fullRole();

    private static Role fullRole() {
        return new Role(1L, "");
    }
}
