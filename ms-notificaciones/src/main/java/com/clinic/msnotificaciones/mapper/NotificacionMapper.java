package com.clinic.msnotificaciones.mapper;

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;
import com.clinic.msnotificaciones.model.Notificacion;

public class NotificacionMapper {

    public static Notificacion toEntity(
            NotificacionRequestDTO dto) {

        return Notificacion.builder()
                .destinatario(dto.getDestinatario())
                .titulo(dto.getTitulo())
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo())
                .estado(dto.getEstado())
                .build();
    }

    public static NotificacionResponseDTO toDTO(
            Notificacion notificacion) {

        return NotificacionResponseDTO.builder()
                .id(notificacion.getId())
                .destinatario(notificacion.getDestinatario())
                .titulo(notificacion.getTitulo())
                .mensaje(notificacion.getMensaje())
                .tipo(notificacion.getTipo())
                .estado(notificacion.getEstado())
                .build();
    }

    public static void updateEntity(
            Notificacion notificacion,
            NotificacionRequestDTO dto) {

        notificacion.setDestinatario(dto.getDestinatario());
        notificacion.setTitulo(dto.getTitulo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipo(dto.getTipo());
        notificacion.setEstado(dto.getEstado());
    }
}