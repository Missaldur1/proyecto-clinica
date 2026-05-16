package com.clinic.msfichasclinicas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.service.FichaClinicaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
@Slf4j
public class FichaClinicaController {

    private final FichaClinicaService service;

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody FichaClinicaRequestDTO dto) {

        log.info(
                "POST /api/fichas");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        log.info(
                "GET /api/fichas");

        return ResponseEntity.ok(
                service.listar());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(

            @PathVariable("id") Long id,

            @Valid @RequestBody FichaClinicaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable("id") Long id) {

        service.eliminar(id);

        return ResponseEntity
                .noContent()
                .build();

    }

}