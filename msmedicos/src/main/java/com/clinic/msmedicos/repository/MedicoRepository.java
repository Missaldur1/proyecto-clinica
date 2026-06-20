package com.clinic.msmedicos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msmedicos.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByRut(String rut);

    Optional<Medico> findByCorreo(String correo);
}