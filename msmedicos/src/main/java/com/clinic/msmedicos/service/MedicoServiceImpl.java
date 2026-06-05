package com.clinic.msmedicos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.exception.MedicoNotFoundException;
import com.clinic.msmedicos.mapper.MedicoMapper;
import com.clinic.msmedicos.model.Medico;
import com.clinic.msmedicos.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository repository;

    // Implementación de listado de médicos utilizando el Mapper para convertir las
    // entidades a DTOs
    @Override
    public List<MedicoResponseDTO> listar() {

        log.info("Listando médicos");
        return repository.findAll()
                .stream()
                .map(MedicoMapper::toDTO)
                .toList();
    }

    // Implementación de búsqueda por ID utilizando el Mapper para convertir la
    // entidad a DTO
    @Override
    public MedicoResponseDTO buscarPorId(Long id) {

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        log.info("Buscando médico ID: {}", id);
        return MedicoMapper.toDTO(medico);
    }

    // Implementación de creación y actualización utilizando el Mapper para
    // convertir
    // entre DTOs y entidades, manteniendo la lógica de negocio en el servicio
    @Override
    public MedicoResponseDTO guardar(MedicoRequestDTO dto) {

        log.info("Creando médico rut: {}", dto.getRut());

        Medico medico = MedicoMapper.toEntity(dto);
        Medico guardado = repository.save(medico);

        log.info("Médico creado correctamente ID: {}", guardado.getId());

        return MedicoMapper.toDTO(guardado);
    }

    @Override
    public MedicoResponseDTO actualizar(Long id, MedicoRequestDTO dto) {
        log.info("Actualizando médico ID: {}", id);

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        MedicoMapper.updateEntity(medico, dto);
        Medico actualizado = repository.save(medico);

        log.info("Médico actualizado correctamente ID: {}", id);

        return MedicoMapper.toDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Intentando eliminar médico ID: {}", id);

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        repository.delete(medico);
        log.info("Médico eliminado correctamente ID: {}", id);
    }
}