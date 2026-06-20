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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Exámenes", description = "Operaciones relacionadas con exámenes médicos")
public class ExamenController {

        private final ExamenService service;

        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
        @PostMapping
        @Tag(name = "Crear Examen", description = "Crea un nuevo examen médico")
        public ResponseEntity<ExamenResponseDTO> crear(
                        @Valid @RequestBody ExamenRequestDTO dto) {

                log.info(
                                "POST /api/examenes");

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(service.crear(dto));

        }

        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
        @GetMapping
        @Tag(name = "Listar Exámenes", description = "Lista todos los exámenes médicos")
        public ResponseEntity<List<ExamenResponseDTO>> listar() {

                log.info(
                                "GET /api/examenes");

                return ResponseEntity.ok(
                                service.listar());

        }

        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO','PACIENTE')")
        @GetMapping("/{id}")
        @Tag(name = "Buscar Examen por ID", description = "Busca un examen médico por su ID")
        public ResponseEntity<ExamenResponseDTO> buscar(
                        @PathVariable("id") Long id) {

                log.info(
                                "GET examen {}", id);

                return ResponseEntity.ok(
                                service.buscarPorId(id));

        }

        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
        @PutMapping("/{id}")
        @Tag(name = "Actualizar Examen por ID", description = "Actualiza un examen médico existente por su ID")
        public ResponseEntity<ExamenResponseDTO> actualizar(

                        @PathVariable("id") Long id,

                        @Valid @RequestBody ExamenRequestDTO dto) {

                log.info(
                                "PUT examen {}", id);

                return ResponseEntity.ok(
                                service.actualizar(
                                                id, dto));

        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        @Tag(name = "Eliminar Examen por ID", description = "Elimina un examen médico existente por su ID")
        public ResponseEntity<Void> eliminar(
                        @PathVariable("id") Long id) {

                log.info(
                                "DELETE examen {}", id);

                service.eliminar(id);

                return ResponseEntity
                                .noContent()
                                .build();

        }

}