package com.clinic.msexamenes.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenResponseDTO {

    private Long id;

    private Long pacienteId;

    private String tipoExamen;

    private LocalDate fecha;

    private String resultado;

    private String estado;

}