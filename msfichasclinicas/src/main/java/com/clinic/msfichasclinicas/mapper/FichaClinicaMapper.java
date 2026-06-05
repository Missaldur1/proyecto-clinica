package com.clinic.msfichasclinicas.mapper;

import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.dto.FichaClinicaResponseDTO;
import com.clinic.msfichasclinicas.model.FichaClinica;

public class FichaClinicaMapper {

        public static FichaClinica toEntity(
                        FichaClinicaRequestDTO dto) {

                return FichaClinica.builder()

                                .pacienteId(dto.getPacienteId())

                                .medicoId(dto.getMedicoId())

                                .examenId(dto.getExamenId())

                                .diagnostico(dto.getDiagnostico())

                                .tratamiento(dto.getTratamiento())

                                .observaciones(dto.getObservaciones())

                                .fecha(dto.getFecha())

                                .build();

        }

        public static FichaClinicaResponseDTO toDTO(
                        FichaClinica ficha) {

                return FichaClinicaResponseDTO
                                .builder()

                                .id(ficha.getId())

                                .pacienteId(
                                                ficha.getPacienteId())

                                .medicoId(
                                                ficha.getMedicoId())

                                .examenId(
                                                ficha.getExamenId())

                                .diagnostico(
                                                ficha.getDiagnostico())

                                .tratamiento(
                                                ficha.getTratamiento())

                                .observaciones(
                                                ficha.getObservaciones())

                                .fecha(
                                                ficha.getFecha())

                                .build();
        }

        public static void updateEntity(
                        FichaClinica ficha,
                        FichaClinicaRequestDTO dto) {

                ficha.setPacienteId(dto.getPacienteId());

                ficha.setMedicoId(dto.getMedicoId());

                ficha.setExamenId(dto.getExamenId());

                ficha.setDiagnostico(dto.getDiagnostico());

                ficha.setTratamiento(dto.getTratamiento());

                ficha.setObservaciones(dto.getObservaciones());

                ficha.setFecha(dto.getFecha());
        }

}