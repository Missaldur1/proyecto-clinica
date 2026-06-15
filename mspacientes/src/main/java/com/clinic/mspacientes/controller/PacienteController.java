package com.clinic.mspacientes.controller;

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

import com.clinic.mspacientes.dto.PacienteRequestDTO;
import com.clinic.mspacientes.dto.PacienteResponseDTO;
import com.clinic.mspacientes.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pacientes", description = "Controlador para la gestión de pacientes")
public class PacienteController {
    private final PacienteService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear paciente", description = """
            Registra un nuevo paciente.

            Roles autorizados:
            - ADMIN

            Requiere todos los datos obligatorios.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<PacienteResponseDTO> crearPaciente(
            @Valid @RequestBody PacienteRequestDTO dto) {

        log.info("POST /api/pacientes");

        return new ResponseEntity<>(
                service.crearPaciente(dto),
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @GetMapping
    @Operation(summary = "Listar pacientes", description = """
            Obtiene todos los pacientes registrados.

            Roles autorizados:
            - ADMIN
            - MEDICO
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {

        log.info("GET /api/pacientes");

        return ResponseEntity.ok(service.listarPacientes());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'PACIENTE')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = """
            Busca un paciente utilizando su identificador.

            Roles autorizados:
            - ADMIN
            - MEDICO
            - PACIENTE
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<PacienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        log.info("GET /api/pacientes/{}", id);

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar paciente por RUT", description = """
            Busca un paciente utilizando su RUT.

            Roles autorizados:
            - ADMIN
            - MEDICO
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<PacienteResponseDTO> buscarPorRut(
            @PathVariable String rut) {

        log.info("GET /api/pacientes/rut/{}", rut);

        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paciente", description = """
            Actualiza los datos de un paciente.

            Roles autorizados:
            - ADMIN
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO dto) {

        log.info("PUT /api/pacientes/{}", id);

        return ResponseEntity.ok(service.actualizarPaciente(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = """
            Elimina un paciente existente.

            Roles autorizados:
            - ADMIN
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<Void> eliminarPaciente(
            @PathVariable Long id) {

        log.info("DELETE /api/pacientes/{}", id);

        service.eliminarPaciente(id);

        return ResponseEntity.noContent().build();
    }

}
