package com.clinic.ms_pagos.dto;

import java.math.BigDecimal;
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
public class PagoResponseDTO {

    private Long id;
    private Long pacienteId;
    private String concepto;
    private BigDecimal monto;
    private String metodoPago;
    private String estado;
    private LocalDate fechaPago;
}