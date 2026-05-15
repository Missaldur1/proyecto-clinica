package com.clinic.msreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msreservas.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByMedicoIdAndFechaAndHora(
            Long medicoId,
            LocalDate fecha,
            LocalTime hora);

}