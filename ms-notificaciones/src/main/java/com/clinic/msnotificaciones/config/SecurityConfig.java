package com.clinic.msnotificaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/notificaciones/**")
                        .hasAnyRole("ADMIN")
                        .anyRequest().authenticated())

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) ->
                                res.setStatus(HttpStatus.UNAUTHORIZED.value())
                        )
                        .accessDeniedHandler((req, res, e) ->
                                res.setStatus(HttpStatus.FORBIDDEN.value())
                        ));

        return http.build();
    }
}