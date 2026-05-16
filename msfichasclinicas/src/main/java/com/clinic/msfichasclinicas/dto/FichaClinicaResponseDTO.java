package com.clinic.msfichasclinicas.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaClinicaResponseDTO {

    private Long id;

    private Long pacienteId;

    private Long medicoId;

    private Long examenId;

    private String diagnostico;

    private String tratamiento;

    private String observaciones;

    private LocalDate fecha;

}