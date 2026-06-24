package com.clinic.msmedicos.dto;

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
@Schema(description = "Información completa de un médico")
public class MedicoResponseDTO {

    @Schema(description = "Identificador único del médico", example = "1")
    private Long id;

    @Schema(description = "RUN del médico", example = "12345678-9")
    private String rut;

    @Schema(description = "Nombre del médico", example = "Pedro")
    private String nombre;

    @Schema(description = "Apellido del médico", example = "González")
    private String apellido;

    @Schema(description = "Especialidad médica", example = "Cardiología")
    private String especialidad;

    @Schema(description = "Correo electrónico", example = "pedro.gonzalez@clinica.cl")
    private String correo;

    @Schema(description = "Número de teléfono", example = "912345678")
    private String telefono;

    @Schema(description = "Indica si el médico está disponible para recibir reservas", example = "true")
    private Boolean disponible;
}