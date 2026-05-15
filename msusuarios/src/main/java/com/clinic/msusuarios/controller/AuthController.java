package com.clinic.msusuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.msusuarios.dto.LoginRequestDTO;
import com.clinic.msusuarios.dto.LoginResponseDTO;
import com.clinic.msusuarios.model.Usuario;
import com.clinic.msusuarios.repository.UsuarioRepository;
import com.clinic.msusuarios.security.jwt.JwtService;

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
            @RequestBody LoginRequestDTO request) {

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRol());

        return ResponseEntity.ok(
                new LoginResponseDTO(token));
    }
}

