package com.clinic.msreservas.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msreservas.client.MedicoClient;
import com.clinic.msreservas.client.PacienteClient;
import com.clinic.msreservas.dto.ReservaRequestDTO;
import com.clinic.msreservas.dto.ReservaResponseDTO;
import com.clinic.msreservas.exception.FechaReservaInvalidaException;
import com.clinic.msreservas.exception.HorarioReservaInvalidoException;
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

                // RN-001: no permitir fechas pasadas
                if (dto.getFecha().isBefore(LocalDate.now())) {

                        log.warn(
                                        "Intento de reserva en fecha pasada {}",
                                        dto.getFecha());

                        throw new FechaReservaInvalidaException();
                }

                // RN-002: horario válido
                if (dto.getHora().isBefore(LocalTime.of(8, 0))
                                || dto.getHora().isAfter(LocalTime.of(18, 0))) {

                        log.warn(
                                        "Horario fuera de rango {}",
                                        dto.getHora());

                        throw new HorarioReservaInvalidoException();
                }

                // validar paciente
                try {

                        pacienteClient.buscarPaciente(
                                        dto.getPacienteId());

                } catch (Exception e) {

                        log.error(
                                        "Paciente {} no encontrado",
                                        dto.getPacienteId());

                        throw new PacienteNotFoundException();
                }

                // validar médico
                try {

                        medicoClient.buscarMedico(
                                        dto.getMedicoId());

                } catch (Exception e) {

                        log.error(
                                        "Médico {} no encontrado",
                                        dto.getMedicoId());

                        throw new MedicoNotFoundException();
                }

                // RN-003: evitar doble reserva
                boolean existe = repository.existsByMedicoIdAndFechaAndHora(
                                dto.getMedicoId(),
                                dto.getFecha(),
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

                // RN-004: toda reserva nace pendiente
                reserva.setEstado("PENDIENTE");

                Reserva guardada = repository.save(reserva);

                log.info(
                                "Reserva creada correctamente ID {}",
                                guardada.getId());

                return ReservaMapper.toDTO(
                                guardada);
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
        public ReservaResponseDTO actualizar(
                        Long id,
                        ReservaRequestDTO dto) {
                        
                log.info(
                                "Actualizando reserva ID {}",
                                id);
                        
                Reserva reserva = repository.findById(id)
                                .orElseThrow(() -> {
                                
                                        log.error(
                                                        "Reserva no encontrada ID {}",
                                                        id);
                                
                                        return new ReservaNotFoundException();
                                
                                });
                        
                // RN-001: no permitir fechas pasadas
                if (dto.getFecha().isBefore(LocalDate.now())) {
                
                        log.warn(
                                        "Intento de actualización con fecha pasada {}",
                                        dto.getFecha());
                
                        throw new FechaReservaInvalidaException();
                }
        
                // RN-002: horario válido
                if (dto.getHora().isBefore(LocalTime.of(8, 0))
                                || dto.getHora().isAfter(LocalTime.of(18, 0))) {
                                
                        log.warn(
                                        "Horario fuera de rango {}",
                                        dto.getHora());
                                
                        throw new HorarioReservaInvalidoException();
                }
        
                // Validar paciente al actualizar
                try {
                
                        pacienteClient.buscarPaciente(
                                        dto.getPacienteId());
                
                } catch (Exception e) {
                
                        log.error(
                                        "Paciente {} no encontrado al actualizar reserva",
                                        dto.getPacienteId());
                
                        throw new PacienteNotFoundException();
                }
        
                // Validar médico al actualizar
                try {
                
                        medicoClient.buscarMedico(
                                        dto.getMedicoId());
                
                } catch (Exception e) {
                
                        log.error(
                                        "Médico {} no encontrado al actualizar reserva",
                                        dto.getMedicoId());
                
                        throw new MedicoNotFoundException();
                }
        
                // RN-003: validar disponibilidad
                boolean existe = repository.existsByMedicoIdAndFechaAndHora(
                                dto.getMedicoId(),
                                dto.getFecha(),
                                dto.getHora());
        
                if (existe
                                && (!reserva.getMedicoId().equals(dto.getMedicoId())
                                                || !reserva.getFecha().equals(dto.getFecha())
                                                || !reserva.getHora().equals(dto.getHora()))) {
                                                
                        log.warn(
                                        "Intento de actualización a horario ocupado. Médico {} fecha {} hora {}",
                                        dto.getMedicoId(),
                                        dto.getFecha(),
                                        dto.getHora());
                                                
                        throw new ReservaDuplicadaException();
                }
        
                ReservaMapper.updateEntity(
                                reserva,
                                dto);
        
                log.info(
                                "Reserva actualizada correctamente");
        
                return ReservaMapper.toDTO(
                                repository.save(reserva));
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