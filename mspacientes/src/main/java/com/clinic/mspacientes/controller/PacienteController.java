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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Slf4j

public class PacienteController {
    private final PacienteService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(
            @Valid @RequestBody PacienteRequestDTO dto) {

        log.info("POST /api/pacientes");

        return new ResponseEntity<>(
                service.crearPaciente(dto),
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {

        log.info("GET /api/pacientes");

        return ResponseEntity.ok(service.listarPacientes());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'PACIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        log.info("GET /api/pacientes/{}", id);

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @GetMapping("/rut/{rut}")
    public ResponseEntity<PacienteResponseDTO> buscarPorRut(
            @PathVariable String rut) {

        log.info("GET /api/pacientes/rut/{}", rut);

        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO dto) {

        log.info("PUT /api/pacientes/{}", id);

        return ResponseEntity.ok(service.actualizarPaciente(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(
            @PathVariable Long id) {

        log.info("DELETE /api/pacientes/{}", id);

        service.eliminarPaciente(id);

        return ResponseEntity.noContent().build();
    }

}
