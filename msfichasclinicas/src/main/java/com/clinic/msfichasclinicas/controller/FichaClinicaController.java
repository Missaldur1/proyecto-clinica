package com.clinic.msfichasclinicas.controller;

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

import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.service.FichaClinicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Fichas Clínicas", description = "Operaciones relacionadas con fichas clínicas")
public class FichaClinicaController {

        private final FichaClinicaService service;

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
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

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @GetMapping
        @Operation(summary = "Listar fichas", description = "Obtiene todas las fichas clínicas registradas")
        public ResponseEntity<?> listar() {

                log.info(
                                "GET /api/fichas");

                return ResponseEntity.ok(
                                service.listar());

        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
        @GetMapping("/{id}")
        @Operation(summary = "Obtener ficha por ID", description = "Retorna una ficha clínica específicada por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
                        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
        })
        public ResponseEntity<?> buscar(
                        @PathVariable("id") Long id) {

                return ResponseEntity.ok(
                                service.buscarPorId(id));

        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @PutMapping("/{id}")
        @Operation(summary = "Actualizar ficha por ID", description = "Modifica una ficha clínica existente específicada por ID")
        public ResponseEntity<?> actualizar(

                        @PathVariable("id") Long id,

                        @Valid @RequestBody FichaClinicaRequestDTO dto) {

                return ResponseEntity.ok(
                                service.actualizar(id, dto));

        }

        @PreAuthorize("hasRole('ADMIN')")
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