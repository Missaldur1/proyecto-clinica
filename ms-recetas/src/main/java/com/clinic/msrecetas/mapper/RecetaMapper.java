package com.clinic.msrecetas.mapper;

import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.model.Receta;

public class RecetaMapper {

    public static Receta toEntity(
            RecetaRequestDTO dto) {

        return Receta.builder()
                .pacienteId(dto.getPacienteId())
                .medicoId(dto.getMedicoId())
                .medicamento(dto.getMedicamento())
                .dosis(dto.getDosis())
                .indicaciones(dto.getIndicaciones())
                .fechaEmision(dto.getFechaEmision())
                .activa(dto.getActiva())
                .build();
    }

    public static RecetaResponseDTO toDTO(
            Receta receta) {

        return RecetaResponseDTO.builder()
                .id(receta.getId())
                .pacienteId(receta.getPacienteId())
                .medicoId(receta.getMedicoId())
                .medicamento(receta.getMedicamento())
                .dosis(receta.getDosis())
                .indicaciones(receta.getIndicaciones())
                .fechaEmision(receta.getFechaEmision())
                .activa(receta.getActiva())
                .build();
    }

    public static void updateEntity(
            Receta receta,
            RecetaRequestDTO dto) {

        receta.setPacienteId(dto.getPacienteId());
        receta.setMedicoId(dto.getMedicoId());
        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setIndicaciones(dto.getIndicaciones());
        receta.setFechaEmision(dto.getFechaEmision());
        receta.setActiva(dto.getActiva());
    }
}