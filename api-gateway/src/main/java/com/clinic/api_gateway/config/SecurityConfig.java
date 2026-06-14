package com.clinic.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.clinic.api_gateway.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

private final JwtAuthenticationFilter jwtFilter;

@Bean
SecurityFilterChain filterChain(HttpSecurity http)
        throws Exception {

    http
        .csrf(csrf -> csrf.disable())

        .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth

                .requestMatchers("/auth/**")
                .permitAll()

                .anyRequest()
                .authenticated())

        .exceptionHandling(ex -> ex

                .authenticationEntryPoint(
                        (request, response, authException) -> {

                            log.warn(
                                    "401 Unauthorized: {} {}",
                                    request.getMethod(),
                                    request.getRequestURI());

                            response.setStatus(
                                    HttpServletResponse.SC_UNAUTHORIZED);

                            response.setContentType(
                                    "application/json");

                            response.getWriter().write("""
                            {
                                "status":401,
                                "error":"Unauthorized",
                                "message":"Token requerido o inválido"
                            }
                            """);
                        })

                .accessDeniedHandler(
                        (request, response, accessDeniedException) -> {

                            log.warn(
                                    "403 Forbidden: {} {}",
                                    request.getMethod(),
                                    request.getRequestURI());

                            response.setStatus(
                                    HttpServletResponse.SC_FORBIDDEN);

                            response.setContentType(
                                    "application/json");

                            response.getWriter().write("""
                            {
                                "status":403,
                                "error":"Forbidden",
                                "message":"No tiene permisos para acceder a este recurso"
                            }
                            """);
                        }))

        .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

}
