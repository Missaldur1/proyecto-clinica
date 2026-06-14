package com.clinic.msrecetas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.exception.RecetaNotFoundException;
import com.clinic.msrecetas.mapper.RecetaMapper;
import com.clinic.msrecetas.model.Receta;
import com.clinic.msrecetas.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository repository;

    @Override
    public List<RecetaResponseDTO> listar() {

        log.info("Listando recetas");

        return repository.findAll()
                .stream()
                .map(RecetaMapper::toDTO)
                .toList();
    }

    @Override
    public RecetaResponseDTO buscarPorId(Long id) {

        log.info("Buscando receta ID: {}", id);

        Receta receta = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Receta no encontrada ID {}",
                            id);

                    return new RecetaNotFoundException();
                });

        return RecetaMapper.toDTO(receta);
    }

    @Override
    public RecetaResponseDTO guardar(
            RecetaRequestDTO dto) {

        log.info(
                "Creando receta para paciente ID: {}",
                dto.getPacienteId());

        Receta receta =
                RecetaMapper.toEntity(dto);

        Receta guardada =
                repository.save(receta);

        log.info(
                "Receta creada correctamente ID: {}",
                guardada.getId());

        return RecetaMapper.toDTO(
                guardada);
    }

    @Override
    public RecetaResponseDTO actualizar(
            Long id,
            RecetaRequestDTO dto) {

        log.info(
                "Actualizando receta ID: {}",
                id);

        Receta receta = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Receta no encontrada ID {}",
                            id);

                    return new RecetaNotFoundException();
                });

        RecetaMapper.updateEntity(
                receta,
                dto);

        Receta actualizada =
                repository.save(receta);

        log.info(
                "Receta actualizada correctamente ID: {}",
                id);

        return RecetaMapper.toDTO(
                actualizada);
    }

    @Override
    public void eliminar(Long id) {

        log.info(
                "Eliminando receta ID: {}",
                id);

        Receta receta = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Receta no encontrada ID {}",
                            id);

                    return new RecetaNotFoundException();
                });

        repository.delete(receta);

        log.info(
                "Receta eliminada correctamente ID: {}",
                id);
    }
}