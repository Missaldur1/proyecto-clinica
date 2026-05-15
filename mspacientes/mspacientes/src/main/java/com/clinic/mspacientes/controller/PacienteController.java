package com.clinic.mspacientes.controller;

import com.clinic.mspacientes.dto.*;
import com.clinic.mspacientes.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Slf4j

public class PacienteController {
    private final PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(
            @Valid @RequestBody PacienteRequestDTO dto) {

        log.info("POST /api/pacientes");

        return new ResponseEntity<>(
                service.crearPaciente(dto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {

        log.info("GET /api/pacientes");

        return ResponseEntity.ok(service.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        log.info("GET /api/pacientes/{}", id);

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<PacienteResponseDTO> buscarPorRut(
            @PathVariable String rut) {

        log.info("GET /api/pacientes/rut/{}", rut);

        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO dto) {

        log.info("PUT /api/pacientes/{}", id);

        return ResponseEntity.ok(service.actualizarPaciente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(
            @PathVariable Long id) {

        log.info("DELETE /api/pacientes/{}", id);

        service.eliminarPaciente(id);

        return ResponseEntity.noContent().build();
    }

}
