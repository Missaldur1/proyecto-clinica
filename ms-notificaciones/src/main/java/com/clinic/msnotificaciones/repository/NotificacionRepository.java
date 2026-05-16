package com.clinic.msnotificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.msnotificaciones.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}