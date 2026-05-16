package com.clinic.msrecetas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.service.RecetaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService service;

    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RecetaResponseDTO> guardar(
            @Valid @RequestBody RecetaRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecetaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}