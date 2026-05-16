package com.clinic.ms_pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/pagos/**")
                        .hasAnyRole("ADMIN", "CAJERO")
                        .anyRequest()
                        .authenticated())

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (request, response, authException) -> {

                                    response.setStatus(
                                            HttpStatus.UNAUTHORIZED.value());

                                    response.getWriter()
                                            .write("No autenticado");
                                })

                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {

                                    response.setStatus(
                                            HttpStatus.FORBIDDEN.value());

                                    response.getWriter()
                                            .write("Acceso denegado");
                                }));

        return http.build();
    }
}