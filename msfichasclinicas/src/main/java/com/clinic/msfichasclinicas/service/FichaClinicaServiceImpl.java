package com.clinic.msfichasclinicas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msfichasclinicas.client.ExamenClient;
import com.clinic.msfichasclinicas.client.MedicoClient;
import com.clinic.msfichasclinicas.client.PacienteClient;
import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.dto.FichaClinicaResponseDTO;
import com.clinic.msfichasclinicas.exception.ExamenNotFoundException;
import com.clinic.msfichasclinicas.exception.FichaNotFoundException;
import com.clinic.msfichasclinicas.exception.MedicoNotFoundException;
import com.clinic.msfichasclinicas.exception.PacienteNotFoundException;
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

        @Override
        public FichaClinicaResponseDTO crear(
                        FichaClinicaRequestDTO dto) {

                log.info(
                                "Creando ficha clínica");

                try {

                        pacienteClient.buscarPaciente(
                                        dto.getPacienteId());

                } catch (Exception e) {

                        log.error(
                                        "Paciente {} no encontrado",
                                        dto.getPacienteId());

                        throw new PacienteNotFoundException(
                                        "No existe el paciente con ID "
                                                        + dto.getPacienteId());
                }

                try {

                        medicoClient.buscarMedico(
                                        dto.getMedicoId());

                } catch (Exception e) {

                        log.error(
                                        "Médico {} no encontrado",
                                        dto.getMedicoId());

                        throw new MedicoNotFoundException(
                                        "No existe el médico con ID "
                                                        + dto.getMedicoId());
                }

                if (dto.getExamenId() != null) {

                        try {

                                examenClient.buscarExamen(
                                                dto.getExamenId());

                        } catch (Exception e) {

                                log.error(
                                                "Examen {} no encontrado",
                                                dto.getExamenId());

                                throw new ExamenNotFoundException(
                                                "No existe el examen con ID "
                                                                + dto.getExamenId());
                        }
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

                log.info("Buscando ficha clínica {}", id);

                FichaClinica ficha = repository.findById(id)
                                .orElseThrow(() -> {

                                        log.error(
                                                        "Ficha clínica {} no encontrada",
                                                        id);

                                        return new FichaNotFoundException();
                                });

                return FichaClinicaMapper.toDTO(ficha);
        }

        @Override
        public FichaClinicaResponseDTO actualizar(
                        Long id,
                        FichaClinicaRequestDTO dto) {
                        
                log.info("Actualizando ficha clínica {}", id);
                        
                FichaClinica ficha = repository.findById(id)
                                .orElseThrow(() -> {
                                
                                        log.error(
                                                        "Ficha clínica {} no encontrada",
                                                        id);
                                
                                        return new FichaNotFoundException();
                                });
                        
                // RN-FIC-01: validar paciente al actualizar
                try {
                
                        pacienteClient.buscarPaciente(
                                        dto.getPacienteId());
                
                } catch (Exception e) {
                
                        log.error(
                                        "Paciente {} no encontrado al actualizar ficha clínica",
                                        dto.getPacienteId());
                
                        throw new PacienteNotFoundException(
                                        "No existe el paciente con ID "
                                                        + dto.getPacienteId());
                }
        
                // RN-FIC-02: validar médico al actualizar
                try {
                
                        medicoClient.buscarMedico(
                                        dto.getMedicoId());
                
                } catch (Exception e) {
                
                        log.error(
                                        "Médico {} no encontrado al actualizar ficha clínica",
                                        dto.getMedicoId());
                
                        throw new MedicoNotFoundException(
                                        "No existe el médico con ID "
                                                        + dto.getMedicoId());
                }
        
                // RN-FIC-03: validar examen al actualizar, solo si viene informado
                if (dto.getExamenId() != null) {
                
                        try {
                        
                                examenClient.buscarExamen(
                                                dto.getExamenId());
                        
                        } catch (Exception e) {
                        
                                log.error(
                                                "Examen {} no encontrado al actualizar ficha clínica",
                                                dto.getExamenId());
                        
                                throw new ExamenNotFoundException(
                                                "No existe el examen con ID "
                                                                + dto.getExamenId());
                        }
                }
        
                FichaClinicaMapper.updateEntity(
                                ficha,
                                dto);
        
                log.info(
                                "Ficha clínica {} actualizada correctamente",
                                id);
        
                return FichaClinicaMapper.toDTO(
                                repository.save(ficha));
        }

        @Override
        public void eliminar(Long id) {

                log.info("Eliminando ficha clínica {}", id);

                FichaClinica ficha = repository.findById(id)
                                .orElseThrow(() -> {

                                        log.error(
                                                        "Ficha clínica {} no encontrada",
                                                        id);

                                        return new FichaNotFoundException();
                                });

                repository.delete(ficha);

                log.info(
                                "Ficha clínica {} eliminada correctamente",
                                id);
        }

}
