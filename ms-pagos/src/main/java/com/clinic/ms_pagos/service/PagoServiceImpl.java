package com.clinic.ms_pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.ms_pagos.client.PacienteClient;
import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;
import com.clinic.ms_pagos.exception.PacienteNotFoundException;
import com.clinic.ms_pagos.exception.PagoNotFoundException;
import com.clinic.ms_pagos.exception.ReglaNegocioException;
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

    private final PacienteClient pacienteClient;

    @Override
    public List<PagoResponseDTO> listar() {

        log.info("Listando pagos");

        return repository.findAll()
                .stream()
                .map(PagoMapper::toDTO)
                .toList();
    }

    @Override
    public PagoResponseDTO buscarPorId(Long id) {

        log.info("Buscando pago ID: {}", id);

        Pago pago = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Pago no encontrado ID {}",
                            id);

                    return new PagoNotFoundException();
                });

        return PagoMapper.toDTO(pago);
    }

    @Override
    public PagoResponseDTO guardar(
            PagoRequestDTO dto) {

        log.info(
                "Registrando pago para paciente ID: {}",
                dto.getPacienteId());

        // RN01: validar existencia del paciente
        try {

            pacienteClient.buscarPaciente(
                    dto.getPacienteId());

        } catch (Exception e) {

            log.error(
                    "Paciente {} no encontrado",
                    dto.getPacienteId());

            throw new PacienteNotFoundException();
        }

        // RN02: validar monto
        if (dto.getMonto().doubleValue() <= 0) {

            log.warn(
                    "Monto inválido {}",
                    dto.getMonto());

            throw new ReglaNegocioException(
                    "El monto debe ser mayor a cero");
        }

        Pago pago =
                PagoMapper.toEntity(dto);

        // RN03: fecha automática
        pago.setFechaPago(
                LocalDate.now());

        // RN04: estado inicial
        pago.setEstado(
                "PENDIENTE");

        Pago guardado =
                repository.save(pago);

        log.info(
                "Pago registrado correctamente ID: {}",
                guardado.getId());

        return PagoMapper.toDTO(
                guardado);
    }

    @Override
    public PagoResponseDTO actualizar(
            Long id,
            PagoRequestDTO dto) {

        log.info(
                "Actualizando pago ID: {}",
                id);

        Pago pago = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Pago no encontrado ID {}",
                            id);

                    return new PagoNotFoundException();
                });

        // RN01: validar existencia del paciente
        try {

            pacienteClient.buscarPaciente(
                    dto.getPacienteId());

        } catch (Exception e) {

            log.error(
                    "Paciente {} no encontrado",
                    dto.getPacienteId());

            throw new PacienteNotFoundException();
        }

        // RN02: validar monto
        if (dto.getMonto().doubleValue() <= 0) {

            throw new ReglaNegocioException(
                    "El monto debe ser mayor a cero");
        }

        PagoMapper.updateEntity(
                pago,
                dto);

        // Mantener fecha original
        // Mantener estado actual

        Pago actualizado =
                repository.save(pago);

        log.info(
                "Pago actualizado correctamente ID: {}",
                id);

        return PagoMapper.toDTO(
                actualizado);
    }

    @Override
    public void eliminar(Long id) {

        log.info(
                "Eliminando pago ID: {}",
                id);

        Pago pago = repository.findById(id)
                .orElseThrow(() -> {

                    log.error(
                            "Pago no encontrado ID {}",
                            id);

                    return new PagoNotFoundException();
                });

        repository.delete(pago);

        log.info(
                "Pago eliminado correctamente ID: {}",
                id);
    }
}