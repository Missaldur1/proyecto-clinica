package com.clinic.msfarmacia.service;
import com.clinic.msfarmacia.dto.*;
import com.clinic.msfarmacia.exception.MedicamentoNotFoundException;
import com.clinic.msfarmacia.model.Medicamento;
import com.clinic.msfarmacia.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository repository;

    @Override
    public List<MedicamentoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public MedicamentoResponseDTO buscarPorId(Long id) {

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(() ->
                        new MedicamentoNotFoundException(
                                "Medicamento no encontrado"));

        return mapToResponseDTO(medicamento);
    }

    @Override
    public MedicamentoResponseDTO guardar(
            MedicamentoRequestDTO dto) {

        Medicamento medicamento = Medicamento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .laboratorio(dto.getLaboratorio())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .disponible(dto.getDisponible())
                .build();

        return mapToResponseDTO(repository.save(medicamento));
    }

    @Override
    public MedicamentoResponseDTO actualizar(
            Long id,
            MedicamentoRequestDTO dto) {

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(() ->
                        new MedicamentoNotFoundException(
                                "Medicamento no encontrado"));

        medicamento.setNombre(dto.getNombre());
        medicamento.setDescripcion(dto.getDescripcion());
        medicamento.setLaboratorio(dto.getLaboratorio());
        medicamento.setPrecio(dto.getPrecio());
        medicamento.setStock(dto.getStock());
        medicamento.setDisponible(dto.getDisponible());

        return mapToResponseDTO(repository.save(medicamento));
    }

    @Override
    public void eliminar(Long id) {

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(() ->
                        new MedicamentoNotFoundException(
                                "Medicamento no encontrado"));

        repository.delete(medicamento);
    }

    private MedicamentoResponseDTO mapToResponseDTO(
            Medicamento medicamento) {

        return MedicamentoResponseDTO.builder()
                .id(medicamento.getId())
                .nombre(medicamento.getNombre())
                .descripcion(medicamento.getDescripcion())
                .laboratorio(medicamento.getLaboratorio())
                .precio(medicamento.getPrecio())
                .stock(medicamento.getStock())
                .disponible(medicamento.getDisponible())
                .build();
    }
}
