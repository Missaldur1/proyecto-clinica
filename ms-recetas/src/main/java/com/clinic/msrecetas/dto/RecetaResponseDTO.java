package com.clinic.msrecetas.dto;

import java.time.LocalDate;

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
public class RecetaResponseDTO {

    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String medicamento;
    private String dosis;
    private String indicaciones;
    private LocalDate fechaEmision;
    private Boolean activa;
}