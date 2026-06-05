package com.clinic.msfichasclinicas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msfichasclinicas.client.*;
import com.clinic.msfichasclinicas.dto.*;
import com.clinic.msfichasclinicas.exception.*;
import com.clinic.msfichasclinicas.mapper.FichaClinicaMapper;
import com.clinic.msfichasclinicas.model.FichaClinica;
import com.clinic.msfichasclinicas.repository.FichaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FichaClinicaServiceImpl
                implements FichaClinicaService {

        private final FichaRepository repository;

        private final PacienteClient pacienteClient;

        private final MedicoClient medicoClient;

        private final ExamenClient examenClient;

        /* RECOMENDACION PARA MEJORAR
        
        @Override
          public FichaClinica crear(FichaDTO dto) {
          
              PacienteDTO paciente =
                      pacienteClient.obtenerPaciente(
                              dto.getPacienteId());
          
              if (paciente == null) {
          
                  throw new PacienteNotFoundException(
                          "No existe el paciente con ID "
                                  + dto.getPacienteId());
              }
          
              FichaClinica ficha = mapper.toEntity(dto);
          
              return repository.save(ficha);
          }*/
        @Override
        public FichaClinicaResponseDTO crear(
                        FichaClinicaRequestDTO dto) {

                log.info(
                                "Creando ficha clínica");

                pacienteClient.buscarPaciente(
                                dto.getPacienteId());

                medicoClient.buscarMedico(
                                dto.getMedicoId());

                if (dto.getExamenId() != null) {

                        examenClient.buscarExamen(
                                        dto.getExamenId());

                }

                FichaClinica ficha = FichaClinicaMapper.toEntity(dto);

                FichaClinica guardada = repository.save(ficha);

                return FichaClinicaMapper
                                .toDTO(guardada);

        }

        @Override
        public List<FichaClinicaResponseDTO> listar() {

                log.info(
                                "Listando fichas");

                return repository.findAll()
                                .stream()
                                .map(
                                                FichaClinicaMapper::toDTO)
                                .toList();

        }

        @Override
        public FichaClinicaResponseDTO buscarPorId(Long id) {

                FichaClinica ficha = repository.findById(id)
                                .orElseThrow(() -> new FichaNotFoundException());

                return FichaClinicaMapper
                                .toDTO(ficha);

        }

        @Override
        public FichaClinicaResponseDTO actualizar(
                        Long id,
                        FichaClinicaRequestDTO dto) {

                FichaClinica ficha = repository.findById(id)
                                .orElseThrow(() -> new FichaNotFoundException());

                ficha.setPacienteId(
                                dto.getPacienteId());

                ficha.setMedicoId(
                                dto.getMedicoId());

                ficha.setExamenId(
                                dto.getExamenId());

                ficha.setDiagnostico(
                                dto.getDiagnostico());

                ficha.setTratamiento(
                                dto.getTratamiento());

                ficha.setObservaciones(
                                dto.getObservaciones());

                ficha.setFecha(
                                dto.getFecha());

                return FichaClinicaMapper
                                .toDTO(
                                                repository.save(ficha));

        }

        @Override
        public void eliminar(Long id) {

                FichaClinica ficha = repository.findById(id)
                                .orElseThrow(() -> new FichaNotFoundException());

                repository.delete(ficha);

        }

}
