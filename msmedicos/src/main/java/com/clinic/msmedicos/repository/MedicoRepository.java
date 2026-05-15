package com.clinic.msmedicos.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msmedicos.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
