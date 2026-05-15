package com.clinic.msreservas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

        private final ReservaService service;

        @PostMapping
        public ResponseEntity<ReservaResponseDTO> crear(
                        @Valid @RequestBody ReservaRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(service.crear(dto));

        }

        @GetMapping
        public ResponseEntity<List<ReservaResponseDTO>> listar() {

                return ResponseEntity.ok(
                                service.listar());

        }

        @GetMapping("/{id}")
        public ResponseEntity<ReservaResponseDTO> buscar(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                service.buscarPorId(id));

        }

        @PutMapping("/{id}")
        public ResponseEntity<ReservaResponseDTO> actualizar(
                        @PathVariable Long id,
                        @Valid @RequestBody ReservaRequestDTO dto) {

                return ResponseEntity.ok(
                                service.actualizar(id, dto));

        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(
                        @PathVariable Long id) {

                service.eliminar(id);

                return ResponseEntity
                                .noContent()
                                .build();

        }

}
