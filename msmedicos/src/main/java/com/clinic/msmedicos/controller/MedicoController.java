package com.clinic.msmedicos.controller;

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

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.service.MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@Tag(name = "Medicos", description = "Controlador para la gestión de médicos")
public class MedicoController {

    private final MedicoService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'PACIENTE')")
    @GetMapping
    @Operation(summary = "Listar médicos", description = """
            Obtiene todos los médicos registrados.

            Roles autorizados:
            - ADMIN
            - MEDICO
            - PACIENTE
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<List<MedicoResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'PACIENTE')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar médico por ID", description = """
            Busca un médico utilizando su identificador.

            Roles autorizados:
            - ADMIN
            - MEDICO
            - PACIENTE
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico encontrado"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<MedicoResponseDTO> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear médico", description = """
            Registra un nuevo médico.

            Roles autorizados:
            - ADMIN
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<MedicoResponseDTO> guardar(
            @RequestBody MedicoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
     @Operation(summary = "Actualizar médico", description = """
            Actualiza los datos de un médico.

            Roles autorizados:
            - ADMIN
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<MedicoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody MedicoRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
     @Operation(summary = "Eliminar médico", description = """
            Elimina un médico existente.

            Roles autorizados:
            - ADMIN
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}