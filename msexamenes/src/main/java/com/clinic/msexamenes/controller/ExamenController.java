package com.clinic.msexamenes.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.clinic.msexamenes.dto.*;
import com.clinic.msexamenes.service.ExamenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@Slf4j
public class ExamenController {

    private final ExamenService service;

    @PostMapping
    public ResponseEntity<ExamenResponseDTO> crear(
            @Valid @RequestBody ExamenRequestDTO dto) {

        log.info(
                "POST /api/examenes");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));

    }

    @GetMapping
    public ResponseEntity<List<ExamenResponseDTO>> listar() {

        log.info(
                "GET /api/examenes");

        return ResponseEntity.ok(
                service.listar());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenResponseDTO> buscar(
            @PathVariable("id") Long id) {

        log.info(
                "GET examen {}", id);

        return ResponseEntity.ok(
                service.buscarPorId(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamenResponseDTO> actualizar(

            @PathVariable("id") Long id,

            @Valid @RequestBody ExamenRequestDTO dto) {

        log.info(
                "PUT examen {}", id);

        return ResponseEntity.ok(
                service.actualizar(
                        id, dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable("id") Long id) {

        log.info(
                "DELETE examen {}", id);

        service.eliminar(id);

        return ResponseEntity
                .noContent()
                .build();

    }

}