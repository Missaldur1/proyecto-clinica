package com.clinic.msreservas.mapper;

import com.clinic.msreservas.dto.ReservaRequestDTO;
import com.clinic.msreservas.dto.ReservaResponseDTO;
import com.clinic.msreservas.model.Reserva;

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

    // Método para actualizar una entidad existente con los datos de un DTO, manteniendo la lógica de actualización
    //centralizada en el Mapper
     public static void updateEntity(
            Reserva reserva,
            ReservaRequestDTO dto) {

        reserva.setPacienteId(dto.getPacienteId());
        reserva.setMedicoId(dto.getMedicoId());
        reserva.setFecha(dto.getFecha());
        reserva.setHora(dto.getHora());
        reserva.setEstado(dto.getEstado());
    }

}