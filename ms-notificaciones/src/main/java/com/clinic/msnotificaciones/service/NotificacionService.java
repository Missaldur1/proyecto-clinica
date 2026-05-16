package com.clinic.msnotificaciones.service;

import java.util.List;

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;

public interface NotificacionService {

    List<NotificacionResponseDTO> listar();

    NotificacionResponseDTO buscarPorId(Long id);

    NotificacionResponseDTO guardar(NotificacionRequestDTO dto);

    NotificacionResponseDTO actualizar(Long id, NotificacionRequestDTO dto);

    void eliminar(Long id);
}