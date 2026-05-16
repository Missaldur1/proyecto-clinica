package com.clinic.mspacientes.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.mspacientes.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByRut(String rut);

}
