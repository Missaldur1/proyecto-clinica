package com.clinic.msfichasclinicas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.service.FichaClinicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Fichas Clínicas", description = "Operaciones relacionadas con fichas clínicas")
public class FichaClinicaController {

        private final FichaClinicaService service;

        @PostMapping
        @Operation(summary = "Crear ficha clínica", description = "Registra una nueva ficha clínica")
        public ResponseEntity<?> crear(
                        @Valid @RequestBody FichaClinicaRequestDTO dto) {

                log.info(
                                "POST /api/fichas");

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(service.crear(dto));
        }

        @GetMapping
        @Operation(summary = "Listar fichas", description = "Obtiene todas las fichas clínicas registradas")
        public ResponseEntity<?> listar() {

                log.info(
                                "GET /api/fichas");

                return ResponseEntity.ok(
                                service.listar());

        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener ficha por ID", description = "Retorna una ficha clínica específicada por ID")
        public ResponseEntity<?> buscar(
                        @PathVariable("id") Long id) {

                return ResponseEntity.ok(
                                service.buscarPorId(id));

        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar ficha por ID", description = "Modifica una ficha clínica existente específicada por ID")
        public ResponseEntity<?> actualizar(

                        @PathVariable("id") Long id,

                        @Valid @RequestBody FichaClinicaRequestDTO dto) {

                return ResponseEntity.ok(
                                service.actualizar(id, dto));

        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar ficha por ID", description = "Elimina una ficha clínica específicada por ID")
        public ResponseEntity<Void> eliminar(
                        @PathVariable("id") Long id) {

                service.eliminar(id);

                return ResponseEntity
                                .noContent()
                                .build();

        }

}