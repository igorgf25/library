package com.igor.library.security;

import com.igor.library.filter.AuthenticationFilter;
import com.igor.library.filter.ValidationFilter;
import com.igor.library.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), jwtUtil);
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/user").permitAll()
                .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .requestMatchers("/v1/login").permitAll()
                .requestMatchers("/h2").permitAll()
                .anyRequest().authenticated())
                .userDetailsService(userDetailsService)
                .addFilter(getAuthenticationFilter())
//                .addFilter(new ValidationFilter(authenticationManager(), userDetailsService, jwtUtil))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/h2/**");
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
