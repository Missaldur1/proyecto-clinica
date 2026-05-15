package com.clinic.msmedicos.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.exception.MedicoNotFoundException;
import com.clinic.msmedicos.model.Medico;
import com.clinic.msmedicos.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository repository;

    @Override
    public List<MedicoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    public MedicoResponseDTO buscarPorId(Long id) {

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        return convertirDTO(medico);
    }

    @Override
    public MedicoResponseDTO guardar(MedicoRequestDTO dto) {

        Medico medico = Medico.builder()
                .rut(dto.getRut())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .especialidad(dto.getEspecialidad())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .disponible(dto.getDisponible())
                .build();

        return convertirDTO(repository.save(medico));
    }

    @Override
    public MedicoResponseDTO actualizar(Long id, MedicoRequestDTO dto) {

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        medico.setRut(dto.getRut());
        medico.setNombre(dto.getNombre());
        medico.setApellido(dto.getApellido());
        medico.setEspecialidad(dto.getEspecialidad());
        medico.setCorreo(dto.getCorreo());
        medico.setTelefono(dto.getTelefono());
        medico.setDisponible(dto.getDisponible());

        return convertirDTO(repository.save(medico));
    }

    @Override
    public void eliminar(Long id) {

        Medico medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));

        repository.delete(medico);
    }

    private MedicoResponseDTO convertirDTO(Medico medico) {

        return MedicoResponseDTO.builder()
                .id(medico.getId())
                .rut(medico.getRut())
                .nombre(medico.getNombre())
                .apellido(medico.getApellido())
                .especialidad(medico.getEspecialidad())
                .correo(medico.getCorreo())
                .telefono(medico.getTelefono())
                .disponible(medico.getDisponible())
                .build();
    }
}