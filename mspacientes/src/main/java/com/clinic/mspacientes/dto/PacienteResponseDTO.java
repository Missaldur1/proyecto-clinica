package com.clinic.mspacientes.dto;

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
@Builder
@Schema(description = "Información completa de un paciente")
public class PacienteResponseDTO {
    @Schema(description = "Identificador único del paciente", example = "1")
    private Long id;

    @Schema(description = "RUN del paciente", example = "12345678-9")
    private String rut;

    @Schema(description = "Nombre del paciente", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del paciente", example = "Pérez")
    private String apellido;

    @Schema(description = "Edad del paciente", example = "30")
    private Integer edad;

    @Schema(description = "Previsión del paciente", example = "Fonasa")
    private String prevision;

    @Schema(description = "Teléfono del paciente", example = "+56912345678")
    private String telefono;

    @Schema(description = "Correo electrónico del paciente", example = "juan.perez@ejemplo.com")
    private String email;

}
