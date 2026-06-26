package com.clinic.msreservas.controller;

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

import com.clinic.msreservas.dto.ReservaRequestDTO;
import com.clinic.msreservas.dto.ReservaResponseDTO;
import com.clinic.msreservas.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones relacionadas con la gestión de reservas médicas")
public class ReservaController {

    private final ReservaService service;

    @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
    @PostMapping
    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva médica asociada a un paciente y un médico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para crear reservas"),
            @ApiResponse(responseCode = "409", description = "Ya existe una reserva en la fecha y hora indicada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaResponseDTO> crear(
            @Valid @RequestBody ReservaRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @GetMapping
    @Operation(summary = "Listar reservas", description = "Obtiene el listado completo de reservas médicas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de reservas obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para listar reservas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID", description = "Busca una reserva médica específica mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada correctamente"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para consultar esta reserva"),
            @ApiResponse(responseCode = "404", description = "No existe una reserva con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaResponseDTO> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva", description = "Actualiza la información de una reserva médica existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para actualizar reservas"),
            @ApiResponse(responseCode = "404", description = "No existe una reserva con el ID indicado"),
            @ApiResponse(responseCode = "409", description = "Conflicto con otra reserva existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva médica existente mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "401", description = "Token no enviado o inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no tiene permisos para eliminar reservas"),
            @ApiResponse(responseCode = "404", description = "No existe una reserva con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}