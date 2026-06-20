package com.clinic.msexamenes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msexamenes.client.PacienteClient;
import com.clinic.msexamenes.dto.ExamenRequestDTO;
import com.clinic.msexamenes.dto.ExamenResponseDTO;
import com.clinic.msexamenes.exception.ExamenNotFoundException;
import com.clinic.msexamenes.exception.PacienteNotFoundException;
import com.clinic.msexamenes.mapper.ExamenMapper;
import com.clinic.msexamenes.model.Examen;
import com.clinic.msexamenes.repository.ExamenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamenServiceImpl
        implements ExamenService {

    private final ExamenRepository repository;

    private final PacienteClient pacienteClient;

    @Override
    public ExamenResponseDTO crear(
            ExamenRequestDTO dto) {

        log.info(
                "Creando examen para paciente {}",
                dto.getPacienteId());

        try {

            pacienteClient.buscarPaciente(
                    dto.getPacienteId());

        } catch (Exception e) {

            log.error(
                    "Paciente {} no encontrado",
                    dto.getPacienteId());

            throw new PacienteNotFoundException(
                    "Paciente no encontrado");
        }

        Examen examen =
                ExamenMapper.toEntity(dto);

        Examen guardado =
                repository.save(examen);

        log.info(
                "Examen creado correctamente ID {}",
                guardado.getId());

        return ExamenMapper.toDTO(
                guardado);
    }

    @Override
    public List<ExamenResponseDTO> listar() {

        log.info(
                "Listando exámenes");

        return repository.findAll()
                .stream()
                .map(ExamenMapper::toDTO)
                .toList();
    }

    @Override
    public ExamenResponseDTO buscarPorId(
            Long id) {

        log.info(
                "Buscando examen ID {}",
                id);

        Examen examen = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Examen no encontrado ID {}",
                            id);

                    return new ExamenNotFoundException();
                });

        return ExamenMapper.toDTO(
                examen);
    }

    @Override
    public ExamenResponseDTO actualizar(
            Long id,
            ExamenRequestDTO dto) {
    
        log.info(
                "Actualizando examen ID {}",
                id);
    
        Examen examen = repository.findById(id)
                .orElseThrow(() -> {
    
                    log.error(
                            "Examen no encontrado ID {}",
                            id);
    
                    return new ExamenNotFoundException();
                });
    
        // RN-EXA-01: validar que el paciente exista también al actualizar
        try {
    
            pacienteClient.buscarPaciente(
                    dto.getPacienteId());
    
        } catch (Exception e) {
    
            log.error(
                    "Paciente {} no encontrado al actualizar examen",
                    dto.getPacienteId());
    
            throw new PacienteNotFoundException(
                    "Paciente no encontrado");
        }
    
        ExamenMapper.updateEntity(
                examen,
                dto);
    
        Examen actualizado =
                repository.save(examen);
    
        log.info(
                "Examen actualizado correctamente ID {}",
                id);
    
        return ExamenMapper.toDTO(
                actualizado);
    }

    @Override
    public void eliminar(
            Long id) {

        log.info(
                "Eliminando examen ID {}",
                id);

        Examen examen = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Examen no encontrado ID {}",
                            id);

                    return new ExamenNotFoundException();
                });

        repository.delete(examen);

        log.info(
                "Examen eliminado correctamente ID {}",
                id);
    }
}