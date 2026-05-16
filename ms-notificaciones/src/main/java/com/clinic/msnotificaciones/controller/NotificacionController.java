package com.clinic.msnotificaciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;
import com.clinic.msnotificaciones.service.NotificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> guardar(
            @Valid @RequestBody NotificacionRequestDTO dto) {

        return ResponseEntity.ok(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}