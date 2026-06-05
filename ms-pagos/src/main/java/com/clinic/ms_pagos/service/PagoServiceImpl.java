package com.clinic.ms_pagos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;
import com.clinic.ms_pagos.exception.PagoNotFoundException;
import com.clinic.ms_pagos.mapper.PagoMapper;
import com.clinic.ms_pagos.model.Pago;
import com.clinic.ms_pagos.repository.PagoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

        private final PagoRepository repository;

        @Override
        public List<PagoResponseDTO> listar() {

                // Se agrega log para seguimiento de la operación de listado de pagos y se
                // utiliza el mapper para convertir las entidades a DTOs de respuesta
                log.info("Listando pagos");

                return repository.findAll()
                                .stream()
                                .map(PagoMapper::toDTO)
                                .toList();
        }

        @Override
        public PagoResponseDTO buscarPorId(Long id) {

                Pago pago = repository.findById(id)
                                .orElseThrow(() -> new PagoNotFoundException(
                                                "Pago no encontrado"));
                log.info("Buscando pago por ID: {}", id);
                return PagoMapper.toDTO(pago);
        }

        @Override
        public PagoResponseDTO guardar(
                        PagoRequestDTO dto) {

                log.info("Registrando pago para paciente ID: {}", dto.getPacienteId());

                Pago pago = PagoMapper.toEntity(dto);

                Pago guardado = repository.save(pago);

                log.info("Pago registrado correctamente ID: {}", guardado.getId());
                return PagoMapper.toDTO(guardado);
        }

        @Override
        public PagoResponseDTO actualizar(
                        Long id,
                        PagoRequestDTO dto) {
                log.info("Actualizando pago ID: {}", id);
                Pago pago = repository.findById(id)
                                .orElseThrow(() -> new PagoNotFoundException(
                                                "Pago no encontrado"));

                Pago actualizado = repository.save(pago);

                log.info("Pago actualizado correctamente");

                return PagoMapper.toDTO(actualizado);
        }

        @Override
        public void eliminar(Long id) {

                Pago pago = repository.findById(id)
                                .orElseThrow(() -> new PagoNotFoundException(
                                                "Pago no encontrado"));
                log.info("Eliminando pago ID: {}", id);
                repository.delete(pago);
                log.info("Pago eliminado correctamente");
        }
}