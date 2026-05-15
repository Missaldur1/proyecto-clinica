package com.clinic.msreservas.service;

import com.clinic.msreservas.client.MedicoClient;
import com.clinic.msreservas.client.PacienteClient;
import com.clinic.msreservas.dto.*;
import com.clinic.msreservas.exception.*;
import com.clinic.msreservas.mapper.ReservaMapper;
import com.clinic.msreservas.model.Reserva;
import com.clinic.msreservas.repository.ReservaRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository repository;

    private final PacienteClient pacienteClient;

    private final MedicoClient medicoClient;

    @Override
    public ReservaResponseDTO crear(ReservaRequestDTO dto) {

        log.info("Creando reserva para paciente {} y médico {}",
                dto.getPacienteId(),
                dto.getMedicoId());

        pacienteClient.buscarPaciente(dto.getPacienteId());

        medicoClient.buscarMedico(dto.getMedicoId());

        boolean existe = repository.existsByMedicoIdAndFechaAndHora(dto.getMedicoId(), dto.getFecha(), dto.getHora());

        if (existe) {

            log.warn(
                    "Intento de reserva duplicada. Médico {} fecha {} hora {}",
                    dto.getMedicoId(),
                    dto.getFecha(),
                    dto.getHora());

            throw new ReservaDuplicadaException();

        }

        Reserva reserva = ReservaMapper.toEntity(dto);

        Reserva guardada = repository.save(reserva);

        log.info("Reserva creada correctamente ID {}",
                guardada.getId());

        return ReservaMapper.toDTO(guardada);
    }

    @Override
    public List<ReservaResponseDTO> listar() {

        log.info("Listando reservas");

        return repository.findAll()
                .stream()
                .map(ReservaMapper::toDTO)
                .toList();

    }

    @Override
    public ReservaResponseDTO buscarPorId(
            Long id) {

        log.info("Buscando reserva ID {}", id);

        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> {

                    log.error("Reserva no encontrada ID {}", id);

                    return new ReservaNotFoundException();

                });

        return ReservaMapper.toDTO(
                reserva);

    }

    @Override
    public ReservaResponseDTO actualizar(
            Long id,
            ReservaRequestDTO dto) {

        log.info("Actualizando reserva ID {}", id);

        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> {

                    log.error("Reserva no encontrada ID {}", id);

                    return new ReservaNotFoundException();

                });

        reserva.setPacienteId(
                dto.getPacienteId());

        reserva.setMedicoId(
                dto.getMedicoId());

        reserva.setFecha(
                dto.getFecha());

        reserva.setHora(
                dto.getHora());

        reserva.setEstado(
                dto.getEstado());

        log.info("Reserva actualizada correctamente");

        return ReservaMapper.toDTO(
                repository.save(reserva));

    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando reserva ID {}", id);

        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> {

                    log.error("Reserva no encontrada ID {}", id);

                    return new ReservaNotFoundException();

                });

        repository.delete(reserva);

        log.info("Reserva eliminada correctamente");

    }
}