package com.clinic.msusuarios.controller;

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

import com.clinic.msusuarios.dto.UsuarioRequestDTO;
import com.clinic.msusuarios.dto.UsuarioResponseDTO;
import com.clinic.msusuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios del sistema")
public class UsuarioController {

        private final UsuarioService usuarioService;

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        @Operation(summary = "Crear usuario", description = """
                        Registra un nuevo usuario.

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
        public ResponseEntity<UsuarioResponseDTO> crearUsuario(
                        @Valid @RequestBody UsuarioRequestDTO dto) {

                log.info("POST /api/usuarios");

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(usuarioService.crearUsuario(dto));
        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @GetMapping
        @Operation(summary = "Listar usuarios", description = """
                        Obtiene todos los usuarios registrados.

                        Roles autorizados:
                        - ADMIN
                        - MEDICO
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Listado obtenido"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "403", description = "Sin permisos")
        })
        public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

                log.info("GET /api/usuarios");

                return ResponseEntity.ok(usuarioService.listarUsuarios());
        }

        @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
        @GetMapping("/{id}")
        @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario específico utilizando su identificador")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        public ResponseEntity<UsuarioResponseDTO> buscarPorId(

                        @Parameter(description = "ID único del usuario", example = "1") @PathVariable Long id) {

                log.info("GET /api/usuarios/{}", id);

                return ResponseEntity.ok(usuarioService.buscarPorId(id));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        @Operation(summary = "Actualizar usuario", description = """
                        Actualiza los datos de un usuario.

                        Roles autorizados:
                        - ADMIN
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Actualizado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "403", description = "Sin permisos")
        })
        public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
                        @Parameter(description = "ID único del usuario", example = "1") @PathVariable Long id,
                        @Valid @RequestBody UsuarioRequestDTO dto) {

                log.info("PUT /api/usuarios/{}", id);

                return ResponseEntity.ok(
                                usuarioService.actualizarUsuario(id, dto));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar usuario", description = """
                        Elimina un usuario existente.

                        Roles autorizados:
                        - ADMIN
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "403", description = "Sin permisos")
        })
        public ResponseEntity<Void> eliminarUsuario(
                        @Parameter(description = "ID único del usuario", example = "1") @PathVariable Long id) {

                log.info("DELETE /api/usuarios/{}", id);

                usuarioService.eliminarUsuario(id);

                return ResponseEntity.noContent().build();
        }
}
