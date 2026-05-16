package com.clinic.ms_pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.ms_pagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}