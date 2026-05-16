package com.clinic.msrecetas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msrecetas.model.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
}