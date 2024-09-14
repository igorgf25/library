package com.igor.library.security;

import com.igor.library.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final User user;

    public UserAuthenticationToken(User user) {
        super(user.getAuthorities());
        this.user = user;
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public User getPrincipal() {
        return user;
    }
}
