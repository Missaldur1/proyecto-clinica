package com.clinic.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth

                                                // Rutas públicas
                                                .requestMatchers(
                                                                "/auth/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // USUARIOS
                                                // Crear, editar y eliminar usuarios: solo ADMIN
                                                .requestMatchers(HttpMethod.POST, "/api/usuarios/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**")
                                                .hasRole("ADMIN")

                                                // Consultar usuarios: ADMIN y MEDICO
                                                .requestMatchers(HttpMethod.GET, "/api/usuarios/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                // PACIENTES
                                                // Crear y eliminar pacientes: ADMIN
                                                .requestMatchers(HttpMethod.POST, "/api/pacientes/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**")
                                                .hasRole("ADMIN")

                                                // Actualizar pacientes: ADMIN y MEDICO
                                                .requestMatchers(HttpMethod.PUT, "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                // Consultar pacientes: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // MEDICOS
                                                // Crear, editar y eliminar médicos: solo ADMIN
                                                .requestMatchers(HttpMethod.POST, "/api/medicos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/api/medicos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/medicos/**")
                                                .hasRole("ADMIN")

                                                // Consultar médicos: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/medicos/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // RESERVAS
                                                // Crear reservas: ADMIN y PACIENTE
                                                .requestMatchers(HttpMethod.POST, "/api/reservas/**")
                                                .hasAnyRole("ADMIN", "PACIENTE")

                                                // Actualizar y eliminar reservas: ADMIN
                                                .requestMatchers(HttpMethod.PUT, "/api/reservas/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/reservas/**")
                                                .hasRole("ADMIN")

                                                // Consultar reservas: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/reservas/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // FICHAS CLINICAS
                                                // Crear, editar y eliminar fichas: ADMIN y MEDICO
                                                .requestMatchers(HttpMethod.POST, "/api/fichas/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.PUT, "/api/fichas/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.DELETE, "/api/fichas/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                // Consultar fichas: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/fichas/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // EXAMENES
                                                // Crear, editar y eliminar exámenes: ADMIN y MEDICO
                                                .requestMatchers(HttpMethod.POST, "/api/examenes/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.PUT, "/api/examenes/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.DELETE, "/api/examenes/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                // Consultar exámenes: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/examenes/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // RECETAS
                                                // Crear, editar y eliminar recetas: ADMIN y MEDICO
                                                .requestMatchers(HttpMethod.POST, "/api/recetas/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.PUT, "/api/recetas/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                .requestMatchers(HttpMethod.DELETE, "/api/recetas/**")
                                                .hasAnyRole("ADMIN", "MEDICO")

                                                // Consultar recetas: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/recetas/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // MEDICAMENTOS / FARMACIA
                                                // Crear, editar y eliminar medicamentos: ADMIN
                                                .requestMatchers(HttpMethod.POST, "/api/medicamentos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/api/medicamentos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/medicamentos/**")
                                                .hasRole("ADMIN")

                                                // Consultar medicamentos: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/medicamentos/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // PAGOS
                                                // Registrar, editar y eliminar pagos: ADMIN
                                                .requestMatchers(HttpMethod.POST, "/api/pagos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/api/pagos/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/pagos/**")
                                                .hasRole("ADMIN")

                                                // Consultar pagos: ADMIN y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/pagos/**")
                                                .hasAnyRole("ADMIN", "PACIENTE")

                                                // NOTIFICACIONES
                                                // Crear, editar y eliminar notificaciones: ADMIN
                                                .requestMatchers(HttpMethod.POST, "/api/notificaciones/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/api/notificaciones/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/api/notificaciones/**")
                                                .hasRole("ADMIN")

                                                // Consultar notificaciones: ADMIN, MEDICO y PACIENTE
                                                .requestMatchers(HttpMethod.GET, "/api/notificaciones/**")
                                                .hasAnyRole("ADMIN", "MEDICO", "PACIENTE")

                                                // Cualquier otra ruta requiere autenticación
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

                                                                        response.getWriter()
                                                                                        .write("""
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

                                                                        response.getWriter()
                                                                                        .write("""
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