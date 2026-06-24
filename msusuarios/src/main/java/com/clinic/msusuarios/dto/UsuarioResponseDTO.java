package com.clinic.msusuarios.dto;

import com.clinic.msusuarios.enums.Rol;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de usuario")
@Builder
public class UsuarioResponseDTO {
    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo", example = "Marco Carrasco")
    private String nombre;

    @Schema(description = "Correo electrónico", example = "marco@clinica.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "ADMIN")
    private Rol rol;

    @Schema(description = "Estado del usuario", example = "true")
    private Boolean activo;
}
