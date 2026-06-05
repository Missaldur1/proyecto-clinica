package com.clinic.msreservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msreservas.client.MedicoClient;
import com.clinic.msreservas.client.PacienteClient;
import com.clinic.msreservas.dto.ReservaRequestDTO;
import com.clinic.msreservas.dto.ReservaResponseDTO;
import com.clinic.msreservas.exception.MedicoNotFoundException;
import com.clinic.msreservas.exception.PacienteNotFoundException;
import com.clinic.msreservas.exception.ReservaDuplicadaException;
import com.clinic.msreservas.exception.ReservaNotFoundException;
import com.clinic.msreservas.mapper.ReservaMapper;
import com.clinic.msreservas.model.Reserva;
import com.clinic.msreservas.repository.ReservaRepository;

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

                // se valida que el paciente y el médico existan en sus respectivos
                // microservicios antes de crear la reserva
                try {

                        pacienteClient.buscarPaciente(
                                        dto.getPacienteId());

                } catch (Exception e) {

                        log.error(
                                        "Paciente {} no encontrado",
                                        dto.getPacienteId());

                        throw new PacienteNotFoundException();
                }

                // se valida que el médico exista en su microservicio antes de crear la reserva
                try {

                        medicoClient.buscarMedico(
                                        dto.getMedicoId());

                } catch (Exception e) {

                        log.error(
                                        "Médico {} no encontrado",
                                        dto.getMedicoId());

                        throw new MedicoNotFoundException();
                }

                boolean existe = repository.existsByMedicoIdAndFechaAndHora(dto.getMedicoId(), dto.getFecha(),
                                dto.getHora());

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

                ReservaMapper.updateEntity(reserva, dto);

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