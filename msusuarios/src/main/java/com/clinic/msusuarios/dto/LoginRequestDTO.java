package com.clinic.msusuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Credenciales de acceso")
@Data
public class LoginRequestDTO {

    @Schema(description = "Correo electrónico del usuario", example = "admin@clinica.com")
    @Email(message = "Formato email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Admin123")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}