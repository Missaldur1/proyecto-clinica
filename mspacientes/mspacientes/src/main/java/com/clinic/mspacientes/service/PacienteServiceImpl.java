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

        Paciente paciente = PacienteMapper.toEntity(dto);

        return PacienteMapper.toDTO(repository.save(paciente));
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente no encontrado"));

        return PacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteResponseDTO buscarPorRut(String rut) {

        log.info("Buscando paciente por rut: {}", rut);

        Paciente paciente = repository.findByRut(rut)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente no encontrado"));

        return PacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteResponseDTO actualizarPaciente(Long id,
                                                  PacienteRequestDTO dto) {

        log.info("Actualizando paciente id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente no encontrado"));

        paciente.setRut(dto.getRut());
        paciente.setNombre(dto.getNombre());
        paciente.setEdad(dto.getEdad());
        paciente.setApellido(dto.getApellido());
        paciente.setPrevision(dto.getPrevision());
        paciente.setTelefono(dto.getTelefono());
        paciente.setEmail(dto.getEmail());

        return PacienteMapper.toDTO(repository.save(paciente));
    }

    @Override
    public void eliminarPaciente(Long id) {

        log.info("Eliminando paciente id: {}", id);

        Paciente paciente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente no encontrado"));

        repository.delete(paciente);
    }
}
