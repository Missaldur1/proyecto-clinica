package com.clinic.msfichasclinicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msfichasclinicas.model.FichaClinica;

public interface FichaRepository
                extends JpaRepository<FichaClinica, Long> {

}