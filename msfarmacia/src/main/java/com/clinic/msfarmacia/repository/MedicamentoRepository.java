package com.clinic.msfarmacia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msfarmacia.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    Optional<Medicamento> findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
            String nombre,
            String laboratorio);
}