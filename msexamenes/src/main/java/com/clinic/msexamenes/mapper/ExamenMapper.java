package com.clinic.msexamenes.mapper;

import com.clinic.msexamenes.dto.ExamenRequestDTO;
import com.clinic.msexamenes.dto.ExamenResponseDTO;
import com.clinic.msexamenes.model.Examen;

public class ExamenMapper {

    public static Examen toEntity(
            ExamenRequestDTO dto) {

        return Examen.builder()
                .pacienteId(dto.getPacienteId())
                .tipoExamen(dto.getTipoExamen())
                .fecha(dto.getFecha())
                .resultado(dto.getResultado())
                .estado(dto.getEstado())
                .build();
    }

    public static ExamenResponseDTO toDTO(
            Examen examen) {

        return ExamenResponseDTO.builder()
                .id(examen.getId())
                .pacienteId(examen.getPacienteId())
                .tipoExamen(examen.getTipoExamen())
                .fecha(examen.getFecha())
                .resultado(examen.getResultado())
                .estado(examen.getEstado())
                .build();
    }

    public static void updateEntity(
            Examen examen,
            ExamenRequestDTO dto) {

        examen.setPacienteId(dto.getPacienteId());
        examen.setTipoExamen(dto.getTipoExamen());
        examen.setFecha(dto.getFecha());
        examen.setResultado(dto.getResultado());
        examen.setEstado(dto.getEstado());
    }
}