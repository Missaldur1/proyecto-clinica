package com.clinic.msusuarios.dto;

import com.clinic.msusuarios.enums.Rol;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos necesarios para registrar un usuario")
public class UsuarioRequestDTO {

    @Schema(description = "Nombre completo", example = "Marco Carrasco")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Correo electrónico", example = "marco@clinica.com")
    @Email(message = "Formato email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Schema(description = "Contraseña", example = "Marco123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    private String password;

    @Schema(description = "Rol del usuario", example = "ADMIN")
    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    @Schema(description = "Estado del usuario", example = "true")
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}