package com.clinic.msrecetas.dto;

import java.time.LocalDate;

import lombok.*;

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