package com.igor.library.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.igor.library.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiration-time}")
    private String tokenExpirationTime;

    @Value("${jwt.secret}")
    private String tokenSecret;

    public String generateJWTToken(User user) {
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(tokenExpirationTime));

        return JWT.create().withSubject(user.getUserId().toString())
                .withExpiresAt(expirationDate)
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withClaim("roles", user.getRoles())
                .sign(Algorithm.HMAC512(tokenSecret));
    }

    public DecodedJWT getUserFromJWT(String token) {
        return JWT.require(Algorithm.HMAC512(tokenSecret))
                .build()
                .verify(token);
    }
}
