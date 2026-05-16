package com.clinic.msexamenes.mapper;

import com.clinic.msexamenes.dto.*;
import com.clinic.msexamenes.model.*;

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

}