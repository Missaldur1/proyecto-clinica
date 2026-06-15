package com.clinic.msfarmacia.controller;
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

import com.clinic.msfarmacia.dto.MedicamentoRequestDTO;
import com.clinic.msfarmacia.dto.MedicamentoResponseDTO;
import com.clinic.msfarmacia.service.MedicamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
@Tag(name = "Medicamentos", description = "Controlador para la gestión de medicamentos")
public class MedicamentoController {

    private final MedicamentoService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO','PACIENTE')")
    @GetMapping
    @Operation(summary = "Listar medicamentos", description = """
            Obtiene todos los medicamentos registrados.

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
    public ResponseEntity<List<MedicamentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO','PACIENTE')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID", description = """
            Busca un medicamento utilizando su identificador.

            Roles autorizados:
            - ADMIN
            - MEDICO
            - PACIENTE
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<MedicamentoResponseDTO> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear medicamento", description = """
            Registra un nuevo medicamento.

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
    public ResponseEntity<MedicamentoResponseDTO> guardar(
            @Valid @RequestBody MedicamentoRequestDTO dto) {

        // se valida el DTO antes de llamar al servicio, si hay errores de validación se devuelve un 400 automáticamente
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.guardar(dto));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar medicamento", description = """
            Actualiza los datos de un medicamento existente.

             Roles autorizados:
            - ADMIN
             """)
    public ResponseEntity<MedicamentoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar medicamento", description = """
            Elimina un medicamento registrado.

             Roles autorizados:
            - ADMIN
             """)
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
