package com.igor.library.util;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.igor.library.model.Role;
import com.igor.library.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserUtil {

    public User decodedJwtToUser(DecodedJWT jwt) {
        return User.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .username(jwt.getClaim("username").toString())
                .password(jwt.getClaim("password").toString())
                .roles(extractRolesFromClaim(jwt))
                .build();
    }

    private List<Role> extractRolesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("roles");
        if (claim.isNull()) return List.of();
        return claim.asList(Role.class);
    }
}
