package com.clinic.msreservas.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private Long id;

    private Long pacienteId;

    private Long medicoId;

    private LocalDate fecha;

    private LocalTime hora;

    private String estado;

}