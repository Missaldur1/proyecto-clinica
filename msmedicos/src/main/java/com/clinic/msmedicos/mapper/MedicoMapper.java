package com.clinic.msmedicos.mapper;

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.model.Medico;

public class MedicoMapper {

    public static Medico toEntity(MedicoRequestDTO dto) {

        return Medico.builder()
                .rut(dto.getRut())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .especialidad(dto.getEspecialidad())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .disponible(dto.getDisponible())
                .build();
    }

    public static MedicoResponseDTO toDTO(Medico medico) {

        return MedicoResponseDTO.builder()
                .id(medico.getId())
                .rut(medico.getRut())
                .nombre(medico.getNombre())
                .apellido(medico.getApellido())
                .especialidad(medico.getEspecialidad())
                .correo(medico.getCorreo())
                .telefono(medico.getTelefono())
                .disponible(medico.getDisponible())
                .build();
    }

    public static void updateEntity(
            Medico medico,
            MedicoRequestDTO dto) {

        medico.setRut(dto.getRut());
        medico.setNombre(dto.getNombre());
        medico.setApellido(dto.getApellido());
        medico.setEspecialidad(dto.getEspecialidad());
        medico.setCorreo(dto.getCorreo());
        medico.setTelefono(dto.getTelefono());
        medico.setDisponible(dto.getDisponible());
    }
}