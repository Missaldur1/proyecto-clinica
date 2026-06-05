package com.clinic.mspacientes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.mspacientes.dto.PacienteRequestDTO;
import com.clinic.mspacientes.dto.PacienteResponseDTO;
import com.clinic.mspacientes.exception.ResourceNotFoundException;
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

        // Convertimos el DTO a entidad utilizando el Mapper
        Paciente paciente = PacienteMapper.toEntity(dto);

        // Guardamos el paciente en la base de datos
        Paciente pacienteGuardado = repository.save(paciente);

        // Agregamos un log para confirmar que el paciente se ha creado correctamente con su ID generado
        log.info("Paciente creado correctamente con ID {}", pacienteGuardado.getId());

        //Por último, convertimos a DTO y lo retornamos
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
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        return PacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteResponseDTO buscarPorRut(String rut) {

        log.info("Buscando paciente por rut: {}", rut);

        Paciente paciente = repository.findByRut(rut)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        return PacienteMapper.toDTO(paciente);
    }

    // Actualización de paciente ahora utilizando Mapper para actualizar solo los
    // campos permitidos y manteniendo la lógica de actualización centralizada en el
    // Mapper
    @Override
    public PacienteResponseDTO actualizarPaciente(
            Long id,
            PacienteRequestDTO dto) {

        log.info("Actualizando paciente id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado"));

        PacienteMapper.updateEntity(paciente, dto);

        Paciente actualizado = repository.save(paciente);

        log.info("Paciente actualizado correctamente con id: {}", id);

        return PacienteMapper.toDTO(actualizado);
    }

    @Override
    public void eliminarPaciente(Long id) {

        log.info("Eliminando paciente id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        repository.delete(paciente);
        log.info("Paciente eliminado correctamente");
    }
}
