package com.clinic.mspacientes.mapper;

import com.clinic.mspacientes.dto.PacienteRequestDTO;
import com.clinic.mspacientes.dto.PacienteResponseDTO;
import com.clinic.mspacientes.model.Paciente;

public class PacienteMapper {
    // Convierte datos recibidos desde el DTO hacia la entidad Paciente
    public static Paciente toEntity(PacienteRequestDTO dto) {

        return Paciente.builder()
                .rut(dto.getRut())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .edad(dto.getEdad())
                .prevision(dto.getPrevision())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .build();
    }

    // Convierte la entidad Paciente hacia un DTO de respuesta
    public static PacienteResponseDTO toDTO(Paciente paciente) {

        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .rut(paciente.getRut())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .edad(paciente.getEdad())
                .prevision(paciente.getPrevision())
                .telefono(paciente.getTelefono())
                .email(paciente.getEmail())
                .build();
    }

    // Método para actualizar una entidad Paciente existente con datos de un DTO, manteniendo la lógica de actualización centralizada en el Mapper
    public static void updateEntity(
            Paciente paciente,
            PacienteRequestDTO dto) {

        paciente.setRut(dto.getRut());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setEdad(dto.getEdad());
        paciente.setPrevision(dto.getPrevision());
        paciente.setTelefono(dto.getTelefono());
        paciente.setEmail(dto.getEmail());
    }

}
