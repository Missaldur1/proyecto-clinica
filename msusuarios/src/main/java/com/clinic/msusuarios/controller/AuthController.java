package com.clinic.msusuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.msusuarios.dto.LoginRequestDTO;
import com.clinic.msusuarios.dto.LoginResponseDTO;
import com.clinic.msusuarios.exception.InvalidCredentialsException;
import com.clinic.msusuarios.model.Usuario;
import com.clinic.msusuarios.repository.UsuarioRepository;
import com.clinic.msusuarios.security.jwt.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final UsuarioRepository usuarioRepository;

        private final PasswordEncoder passwordEncoder;

        private final JwtService jwtService;

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(
                        @Valid @RequestBody LoginRequestDTO request) {

                Usuario user = usuarioRepository
                                .findByEmail(request.getEmail())
                                .orElseThrow(
                                                InvalidCredentialsException::new);

                if (!passwordEncoder.matches(
                                request.getPassword(),
                                user.getPassword())) {

                        throw new InvalidCredentialsException();
                }

                String token = jwtService.generateToken(
                                user.getEmail(),
                                user.getRol().name());

                return ResponseEntity.ok(
                                new LoginResponseDTO(token));
        }
}