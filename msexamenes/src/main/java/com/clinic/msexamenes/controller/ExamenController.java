package com.clinic.msexamenes.controller;

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

import com.clinic.msexamenes.dto.ExamenRequestDTO;
import com.clinic.msexamenes.dto.ExamenResponseDTO;
import com.clinic.msexamenes.service.ExamenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Exámenes", description = "Operaciones relacionadas con la gestión de exámenes médicos")
public class ExamenController {

    private final ExamenService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @PostMapping
    @Operation(summary = "Crear examen", description = "Registra un nuevo examen médico asociado a un paciente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Examen creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para crear exámenes"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ExamenResponseDTO> crear(
            @Valid @RequestBody ExamenRequestDTO dto) {

        log.info("POST /api/examenes");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @GetMapping
    @Operation(summary = "Listar exámenes", description = "Obtiene el listado completo de exámenes médicos registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de exámenes obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para listar exámenes"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ExamenResponseDTO>> listar() {

        log.info("GET /api/examenes");

        return ResponseEntity.ok(
                service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO','PACIENTE')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar examen por ID", description = "Busca un examen médico específico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Examen encontrado correctamente"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para consultar este examen"),
            @ApiResponse(responseCode = "404", description = "No existe un examen con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ExamenResponseDTO> buscar(
            @PathVariable("id") Long id) {

        log.info("GET examen {}", id);

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar examen", description = "Actualiza la información de un examen médico existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Examen actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para actualizar exámenes"),
            @ApiResponse(responseCode = "404", description = "No existe un examen con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ExamenResponseDTO> actualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExamenRequestDTO dto) {

        log.info("PUT examen {}", id);

        return ResponseEntity.ok(
                service.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar examen", description = "Elimina un examen médico existente mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Examen eliminado correctamente"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para eliminar exámenes"),
            @ApiResponse(responseCode = "404", description = "No existe un examen con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable("id") Long id) {

        log.info("DELETE examen {}", id);

        service.eliminar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}