package com.clinic.msfarmacia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msfarmacia.dto.MedicamentoRequestDTO;
import com.clinic.msfarmacia.dto.MedicamentoResponseDTO;
import com.clinic.msfarmacia.exception.MedicamentoDuplicadoException;
import com.clinic.msfarmacia.exception.MedicamentoNotFoundException;
import com.clinic.msfarmacia.mapper.MedicamentoMapper;
import com.clinic.msfarmacia.model.Medicamento;
import com.clinic.msfarmacia.repository.MedicamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository repository;

    @Override
    public List<MedicamentoResponseDTO> listar() {

        log.info("Listando medicamentos");

        return repository.findAll()
                .stream()
                .map(MedicamentoMapper::toDTO)
                .toList();
    }

    @Override
    public MedicamentoResponseDTO buscarPorId(Long id) {

        log.info("Buscando medicamento ID: {}", id);

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(MedicamentoNotFoundException::new);

        return MedicamentoMapper.toDTO(medicamento);
    }

    @Override
    public MedicamentoResponseDTO guardar(
            MedicamentoRequestDTO dto) {

        log.info("Creando medicamento: {}", dto.getNombre());

        repository.findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                dto.getNombre(),
                dto.getLaboratorio())
                .ifPresent(medicamento -> {

                    log.warn(
                            "Intento de registro de medicamento duplicado: {} - {}",
                            dto.getNombre(),
                            dto.getLaboratorio());

                    throw new MedicamentoDuplicadoException(
                            "Ya existe un medicamento registrado con el nombre "
                                    + dto.getNombre()
                                    + " del laboratorio "
                                    + dto.getLaboratorio());
                });

        Medicamento medicamento = MedicamentoMapper.toEntity(dto);

        Medicamento guardado = repository.save(medicamento);

        log.info(
                "Medicamento creado correctamente ID: {}",
                guardado.getId());

        return MedicamentoMapper.toDTO(guardado);
    }

    @Override
    public MedicamentoResponseDTO actualizar(
            Long id,
            MedicamentoRequestDTO dto) {

        log.info("Actualizando medicamento ID: {}", id);

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(MedicamentoNotFoundException::new);

        repository.findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                dto.getNombre(),
                dto.getLaboratorio())
                .filter(medicamentoExistente -> !medicamentoExistente.getId().equals(id))
                .ifPresent(medicamentoExistente -> {

                    log.warn(
                            "Intento de actualizar medicamento ID {} con datos duplicados: {} - {}",
                            id,
                            dto.getNombre(),
                            dto.getLaboratorio());

                    throw new MedicamentoDuplicadoException(
                            "Ya existe otro medicamento registrado con el nombre "
                                    + dto.getNombre()
                                    + " del laboratorio "
                                    + dto.getLaboratorio());
                });

        MedicamentoMapper.updateEntity(
                medicamento,
                dto);

        Medicamento actualizado = repository.save(medicamento);

        log.info(
                "Medicamento actualizado correctamente ID: {}",
                id);

        return MedicamentoMapper.toDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando medicamento ID: {}", id);

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(MedicamentoNotFoundException::new);

        repository.delete(medicamento);

        log.info(
                "Medicamento eliminado correctamente ID: {}",
                id);
    }
}