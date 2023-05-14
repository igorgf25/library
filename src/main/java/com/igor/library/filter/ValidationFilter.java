package com.igor.library.filter;

import com.igor.library.security.UserDetailsServiceImpl;
import com.igor.library.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidationFilter extends BasicAuthenticationFilter {

    private final UserDetailsServiceImpl service;
    private JwtUtil jwtUtil;
    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String tokenSecret;

    public ValidationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl service, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String attribute = request.getHeader(HEADER_ATTRIBUTE);

        if (attribute == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!attribute.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = attribute.replace(TOKEN_PREFIX, "");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = jwtUtil.getUserFromJWT(token);

        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null,
                service.loadUserByUsername(user).getAuthorities());
    }
}
