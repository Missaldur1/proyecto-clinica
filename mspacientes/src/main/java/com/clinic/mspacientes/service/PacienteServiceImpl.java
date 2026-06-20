package com.clinic.mspacientes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.mspacientes.dto.PacienteRequestDTO;
import com.clinic.mspacientes.dto.PacienteResponseDTO;
import com.clinic.mspacientes.exception.PacienteDuplicadoException;
import com.clinic.mspacientes.exception.PacienteNotFoundException;
import com.clinic.mspacientes.mapper.PacienteMapper;
import com.clinic.mspacientes.model.Paciente;
import com.clinic.mspacientes.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;

    @Override
    public PacienteResponseDTO crearPaciente(PacienteRequestDTO dto) {

        log.info("Creando paciente con rut: {}", dto.getRut());

        if (repository.findByRut(dto.getRut()).isPresent()) {

            log.warn(
                    "Intento de registro con RUT duplicado: {}",
                    dto.getRut());

            throw new PacienteDuplicadoException(
                    "Ya existe un paciente registrado con el RUT " + dto.getRut());
        }

        Paciente paciente = PacienteMapper.toEntity(dto);

        Paciente pacienteGuardado = repository.save(paciente);

        log.info(
                "Paciente creado correctamente con ID {}",
                pacienteGuardado.getId());

        return PacienteMapper.toDTO(pacienteGuardado);
    }

    @Override
    public List<PacienteResponseDTO> listarPacientes() {

        log.info("Listando pacientes");

        return repository.findAll()
                .stream()
                .map(PacienteMapper::toDTO)
                .toList();
    }

    @Override
    public PacienteResponseDTO buscarPorId(Long id) {

        log.info("Buscando paciente por id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente no encontrado"));

        return PacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteResponseDTO buscarPorRut(String rut) {

        log.info("Buscando paciente por rut: {}", rut);

        Paciente paciente = repository.findByRut(rut)
                .orElseThrow(PacienteNotFoundException::new);

        return PacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteResponseDTO actualizarPaciente(
            Long id,
            PacienteRequestDTO dto) {

        log.info("Actualizando paciente id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(PacienteNotFoundException::new);

        repository.findByRut(dto.getRut())
                .filter(pacienteExistente -> !pacienteExistente.getId().equals(id))
                .ifPresent(pacienteExistente -> {

                    log.warn(
                            "Intento de actualizar paciente ID {} con RUT duplicado {}",
                            id,
                            dto.getRut());

                    throw new PacienteDuplicadoException(
                            "Ya existe otro paciente registrado con el RUT " + dto.getRut());
                });

        PacienteMapper.updateEntity(paciente, dto);

        Paciente actualizado = repository.save(paciente);

        log.info(
                "Paciente actualizado correctamente con id: {}",
                id);

        return PacienteMapper.toDTO(actualizado);
    }

    @Override
    public void eliminarPaciente(Long id) {

        log.info("Eliminando paciente id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(PacienteNotFoundException::new);

        repository.delete(paciente);

        log.info("Paciente eliminado correctamente");
    }
}