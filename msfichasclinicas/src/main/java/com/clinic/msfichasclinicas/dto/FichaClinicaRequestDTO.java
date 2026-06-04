package com.clinic.msfichasclinicas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaClinicaRequestDTO {

    @NotNull
    @Schema(description = "ID del paciente asociado", example = "1")
    private Long pacienteId;

    @NotNull
    private Long medicoId;

    private Long examenId;

    @NotBlank
    @Schema(description = "Diagnóstico médico", example = "Dolor torácico")
    private String diagnostico;

    @NotBlank
    @Schema(description = "Tratamiento indicado", example = "Reposo absoluto durante 7 días")
    private String tratamiento;

    private String observaciones;

    @NotNull
    private LocalDate fecha;

}