package com.clinic.msmedicos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.exception.MedicoDuplicadoException;
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

    @Override
    public List<MedicoResponseDTO> listar() {

        log.info("Listando médicos");

        return repository.findAll()
                .stream()
                .map(MedicoMapper::toDTO)
                .toList();
    }

    @Override
    public MedicoResponseDTO buscarPorId(Long id) {

        log.info("Buscando médico ID: {}", id);

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        return MedicoMapper.toDTO(medico);
    }

    @Override
    public MedicoResponseDTO guardar(MedicoRequestDTO dto) {

        log.info("Creando médico rut: {}", dto.getRut());

        repository.findByRut(dto.getRut())
                .ifPresent(medico -> {

                    log.warn(
                            "Intento de registro con RUT duplicado: {}",
                            dto.getRut());

                    throw new MedicoDuplicadoException(
                            "Ya existe un médico registrado con el RUT " + dto.getRut());
                });

        repository.findByCorreo(dto.getCorreo())
                .ifPresent(medico -> {

                    log.warn(
                            "Intento de registro con correo duplicado: {}",
                            dto.getCorreo());

                    throw new MedicoDuplicadoException(
                            "Ya existe un médico registrado con el correo " + dto.getCorreo());
                });

        Medico medico = MedicoMapper.toEntity(dto);

        Medico guardado = repository.save(medico);

        log.info(
                "Médico creado correctamente ID: {}",
                guardado.getId());

        return MedicoMapper.toDTO(guardado);
    }

    @Override
    public MedicoResponseDTO actualizar(
            Long id,
            MedicoRequestDTO dto) {

        log.info("Actualizando médico ID: {}", id);

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        repository.findByRut(dto.getRut())
                .filter(medicoExistente -> !medicoExistente.getId().equals(id))
                .ifPresent(medicoExistente -> {

                    log.warn(
                            "Intento de actualizar médico ID {} con RUT duplicado {}",
                            id,
                            dto.getRut());

                    throw new MedicoDuplicadoException(
                            "Ya existe otro médico registrado con el RUT " + dto.getRut());
                });

        repository.findByCorreo(dto.getCorreo())
                .filter(medicoExistente -> !medicoExistente.getId().equals(id))
                .ifPresent(medicoExistente -> {

                    log.warn(
                            "Intento de actualizar médico ID {} con correo duplicado {}",
                            id,
                            dto.getCorreo());

                    throw new MedicoDuplicadoException(
                            "Ya existe otro médico registrado con el correo " + dto.getCorreo());
                });

        MedicoMapper.updateEntity(
                medico,
                dto);

        Medico actualizado = repository.save(medico);

        log.info(
                "Médico actualizado correctamente ID: {}",
                id);

        return MedicoMapper.toDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Intentando eliminar médico ID: {}", id);

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        repository.delete(medico);

        log.info(
                "Médico eliminado correctamente ID: {}",
                id);
    }
}