package com.clinic.msreservas.mapper;

import com.clinic.msreservas.dto.*;
import com.clinic.msreservas.model.*;

public class ReservaMapper {

    public static Reserva toEntity(ReservaRequestDTO dto) {

        return Reserva.builder()
                .pacienteId(dto.getPacienteId())
                .medicoId(dto.getMedicoId())
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .estado(dto.getEstado())
                .build();
    }

    public static ReservaResponseDTO toDTO(Reserva reserva) {

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .pacienteId(reserva.getPacienteId())
                .medicoId(reserva.getMedicoId())
                .fecha(reserva.getFecha())
                .hora(reserva.getHora())
                .estado(reserva.getEstado())
                .build();
    }

}