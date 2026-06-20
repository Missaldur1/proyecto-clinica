package com.clinic.ms_pagos.mapper;

import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;
import com.clinic.ms_pagos.model.Pago;

public class PagoMapper {

    public static void updateEntity(
            Pago pago,
            PagoRequestDTO dto) {

        pago.setPacienteId(dto.getPacienteId());
        pago.setConcepto(dto.getConcepto());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
    }

    public static PagoResponseDTO toDTO(
            Pago pago) {

        return PagoResponseDTO.builder()
                .id(pago.getId())
                .pacienteId(pago.getPacienteId())
                .concepto(pago.getConcepto())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodoPago())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .build();
    }

    public static Pago toEntity(
            PagoRequestDTO dto) {

        return Pago.builder()
                .pacienteId(dto.getPacienteId())
                .concepto(dto.getConcepto())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .build();
    }
}