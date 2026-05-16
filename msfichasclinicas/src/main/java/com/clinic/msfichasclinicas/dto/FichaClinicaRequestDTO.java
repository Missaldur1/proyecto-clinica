package com.clinic.msfichasclinicas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaClinicaRequestDTO {

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long medicoId;

    private Long examenId;

    @NotBlank
    private String diagnostico;

    @NotBlank
    private String tratamiento;

    private String observaciones;

    @NotNull
    private LocalDate fecha;

}