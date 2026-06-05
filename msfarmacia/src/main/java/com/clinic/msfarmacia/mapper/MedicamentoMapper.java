package com.clinic.msfarmacia.mapper;

import com.clinic.msfarmacia.dto.MedicamentoRequestDTO;
import com.clinic.msfarmacia.dto.MedicamentoResponseDTO;
import com.clinic.msfarmacia.model.Medicamento;

public class MedicamentoMapper {

    public static Medicamento toEntity(
            MedicamentoRequestDTO dto) {

        return Medicamento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .laboratorio(dto.getLaboratorio())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .disponible(dto.getDisponible())
                .build();
    }

    public static MedicamentoResponseDTO toDTO(
            Medicamento medicamento) {

        return MedicamentoResponseDTO.builder()
                .id(medicamento.getId())
                .nombre(medicamento.getNombre())
                .descripcion(medicamento.getDescripcion())
                .laboratorio(medicamento.getLaboratorio())
                .precio(medicamento.getPrecio())
                .stock(medicamento.getStock())
                .disponible(medicamento.getDisponible())
                .build();
    }

    public static void updateEntity(
            Medicamento medicamento,
            MedicamentoRequestDTO dto) {

        medicamento.setNombre(dto.getNombre());
        medicamento.setDescripcion(dto.getDescripcion());
        medicamento.setLaboratorio(dto.getLaboratorio());
        medicamento.setPrecio(dto.getPrecio());
        medicamento.setStock(dto.getStock());
        medicamento.setDisponible(dto.getDisponible());
    }
}