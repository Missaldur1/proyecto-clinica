package com.clinic.msrecetas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.exception.RecetaNotFoundException;
import com.clinic.msrecetas.model.Receta;
import com.clinic.msrecetas.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository repository;

    @Override
    public List<RecetaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public RecetaResponseDTO buscarPorId(Long id) {

        Receta receta = repository.findById(id)
                .orElseThrow(() ->
                        new RecetaNotFoundException(
                                "Receta no encontrada"));

        return mapToResponseDTO(receta);
    }

    @Override
    public RecetaResponseDTO guardar(
            RecetaRequestDTO dto) {

        Receta receta = Receta.builder()
                .pacienteId(dto.getPacienteId())
                .medicoId(dto.getMedicoId())
                .medicamento(dto.getMedicamento())
                .dosis(dto.getDosis())
                .indicaciones(dto.getIndicaciones())
                .fechaEmision(dto.getFechaEmision())
                .activa(dto.getActiva())
                .build();

        return mapToResponseDTO(repository.save(receta));
    }

    @Override
    public RecetaResponseDTO actualizar(
            Long id,
            RecetaRequestDTO dto) {

        Receta receta = repository.findById(id)
                .orElseThrow(() ->
                        new RecetaNotFoundException(
                                "Receta no encontrada"));

        receta.setPacienteId(dto.getPacienteId());
        receta.setMedicoId(dto.getMedicoId());
        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setIndicaciones(dto.getIndicaciones());
        receta.setFechaEmision(dto.getFechaEmision());
        receta.setActiva(dto.getActiva());

        return mapToResponseDTO(repository.save(receta));
    }

    @Override
    public void eliminar(Long id) {

        Receta receta = repository.findById(id)
                .orElseThrow(() ->
                        new RecetaNotFoundException(
                                "Receta no encontrada"));

        repository.delete(receta);
    }

    private RecetaResponseDTO mapToResponseDTO(
            Receta receta) {

        return RecetaResponseDTO.builder()
                .id(receta.getId())
                .pacienteId(receta.getPacienteId())
                .medicoId(receta.getMedicoId())
                .medicamento(receta.getMedicamento())
                .dosis(receta.getDosis())
                .indicaciones(receta.getIndicaciones())
                .fechaEmision(receta.getFechaEmision())
                .activa(receta.getActiva())
                .build();
    }
}