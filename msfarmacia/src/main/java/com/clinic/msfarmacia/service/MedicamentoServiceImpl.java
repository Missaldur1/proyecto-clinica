package com.clinic.msfarmacia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msfarmacia.dto.MedicamentoRequestDTO;
import com.clinic.msfarmacia.dto.MedicamentoResponseDTO;
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
                // Implementación de listado de medicamentos utilizando el Mapper para convertir
                // las entidades a DTOs
                log.info("Listando medicamentos");

                return repository.findAll()
                                .stream()
                                .map(MedicamentoMapper::toDTO)
                                .toList();
        }

        @Override
        public MedicamentoResponseDTO buscarPorId(Long id) {

                Medicamento medicamento = repository.findById(id)
                                .orElseThrow(MedicamentoNotFoundException::new);
                // Implementación de búsqueda por ID utilizando el Mapper para convertir la
                // entidad a DTO
                log.info("Buscando medicamento ID: {}", id);
                return MedicamentoMapper.toDTO(medicamento);
        }

        @Override
        public MedicamentoResponseDTO guardar(
                        MedicamentoRequestDTO dto) {

                // Implementación de creación utilizando el Mapper para convertir entre DTOs y
                // entidades, manteniendo la lógica de negocio en el servicio
                log.info("Creando medicamento: {}", dto.getNombre());
                Medicamento medicamento = MedicamentoMapper.toEntity(dto);

                Medicamento guardado = repository.save(medicamento);

                log.info("Medicamento creado correctamente ID: {}", guardado.getId());
                return MedicamentoMapper.toDTO(guardado);
        }

        @Override
        public MedicamentoResponseDTO actualizar(
                        Long id,
                        MedicamentoRequestDTO dto) {

                Medicamento medicamento = repository.findById(id)
                                .orElseThrow(MedicamentoNotFoundException::new);

                // Se agregaron logs para seguimiento del proceso de actualización
                log.info("Actualizando medicamento ID: {}", id);

                MedicamentoMapper.updateEntity(medicamento, dto);
                Medicamento actualizado = repository.save(medicamento);

                log.info("Medicamento actualizado correctamente");
                return MedicamentoMapper.toDTO(actualizado);
        }

        @Override
        public void eliminar(Long id) {

                Medicamento medicamento = repository.findById(id)
                                .orElseThrow(MedicamentoNotFoundException::new);
                // Se agregaron logs para seguimiento del proceso de eliminación
                log.info("Eliminando medicamento ID: {}", id);

                repository.delete(medicamento);
                log.info("Medicamento eliminado correctamente");
        }

}
