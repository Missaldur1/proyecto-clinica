package com.clinic.msexamenes.repository;

import com.clinic.msexamenes.model.Examen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamenRepository
        extends JpaRepository<Examen, Long> {

}