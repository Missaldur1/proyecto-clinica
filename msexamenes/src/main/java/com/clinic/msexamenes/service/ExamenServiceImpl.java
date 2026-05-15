package com.clinic.msexamenes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msexamenes.client.PacienteClient;
import com.clinic.msexamenes.dto.*;
import com.clinic.msexamenes.exception.ExamenNotFoundException;
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
                "Creando examen paciente {}",
                dto.getPacienteId());

        pacienteClient.buscarPaciente(
                dto.getPacienteId());

        Examen examen = ExamenMapper.toEntity(dto);

        Examen guardado = repository.save(examen);

        log.info(
                "Examen creado ID {}",
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
    public ExamenResponseDTO buscarPorId(Long id) {

        log.info(
                "Buscando examen {}", id);

        Examen examen = repository.findById(id)
                .orElseThrow(() -> new ExamenNotFoundException());

        return ExamenMapper.toDTO(
                examen);

    }

    @Override
    public ExamenResponseDTO actualizar(
            Long id,
            ExamenRequestDTO dto) {

        log.info(
                "Actualizando examen {}", id);

        Examen examen = repository.findById(id)
                .orElseThrow(() -> new ExamenNotFoundException());

        examen.setPacienteId(
                dto.getPacienteId());

        examen.setTipoExamen(
                dto.getTipoExamen());

        examen.setFecha(
                dto.getFecha());

        examen.setResultado(
                dto.getResultado());

        examen.setEstado(
                dto.getEstado());

        return ExamenMapper.toDTO(
                repository.save(examen));

    }

    @Override
    public void eliminar(Long id) {

        log.info(
                "Eliminando examen {}", id);

        Examen examen = repository.findById(id)
                .orElseThrow(() -> new ExamenNotFoundException());

        repository.delete(examen);

    }

}