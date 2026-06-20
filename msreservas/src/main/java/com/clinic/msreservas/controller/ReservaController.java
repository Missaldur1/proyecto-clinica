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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones relacionadas con reservas")
public class ReservaController {

        private final ReservaService service;

        @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
        @PostMapping
        @Tag(name = "Crear Reservas", description = "Crear una nueva reserva")
        public ResponseEntity<ReservaResponseDTO> crear(
                        @Valid @RequestBody ReservaRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(service.crear(dto));

        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @GetMapping
        @Tag(name = "Listar Reservas", description = "Listar todas las reservas")
        public ResponseEntity<List<ReservaResponseDTO>> listar() {

                return ResponseEntity.ok(
                                service.listar());

        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
        @GetMapping("/{id}")
        @Tag(name = "Buscar Reserva", description = "Buscar una reserva por su ID")
        public ResponseEntity<ReservaResponseDTO> buscar(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                service.buscarPorId(id));

        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @PutMapping("/{id}")
        @Tag(name = "Actualizar Reserva", description = "Actualizar una reserva existente por su ID")
        public ResponseEntity<ReservaResponseDTO> actualizar(
                        @PathVariable Long id,
                        @Valid @RequestBody ReservaRequestDTO dto) {

                return ResponseEntity.ok(
                                service.actualizar(id, dto));

        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @DeleteMapping("/{id}")
        @Tag(name = "Eliminar Reserva", description = "Eliminar una reserva por su ID")
        public ResponseEntity<Void> eliminar(
                        @PathVariable Long id) {

                service.eliminar(id);

                return ResponseEntity
                                .noContent()
                                .build();

        }

}
