package com.clinic.msusuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.msusuarios.dto.UsuarioRequestDTO;
import com.clinic.msusuarios.dto.UsuarioResponseDTO;
import com.clinic.msusuarios.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j

public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        log.info("POST /api/usuarios");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

        log.info("GET /api/usuarios");

        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable Long id) {

        log.info("GET /api/usuarios/{}", id);

        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        log.info("PUT /api/usuarios/{}", id);

        return ResponseEntity.ok(
                usuarioService.actualizarUsuario(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Long id) {

        log.info("DELETE /api/usuarios/{}", id);

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
