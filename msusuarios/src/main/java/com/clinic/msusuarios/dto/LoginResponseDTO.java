package com.clinic.msusuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Respuesta de autenticación")
@Data
@AllArgsConstructor
public class LoginResponseDTO {

    @Schema(description = "Token JWT generado", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
}
