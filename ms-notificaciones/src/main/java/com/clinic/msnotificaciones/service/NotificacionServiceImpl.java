package com.clinic.msnotificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;
import com.clinic.msnotificaciones.exception.NotificacionNotFoundException;
import com.clinic.msnotificaciones.model.Notificacion;
import com.clinic.msnotificaciones.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;

    @Override
    public List<NotificacionResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public NotificacionResponseDTO buscarPorId(Long id) {

        Notificacion n = repository.findById(id)
                .orElseThrow(() ->
                        new NotificacionNotFoundException("Notificación no encontrada"));

        return mapToDTO(n);
    }

    @Override
    public NotificacionResponseDTO guardar(NotificacionRequestDTO dto) {

        Notificacion n = Notificacion.builder()
                .destinatario(dto.getDestinatario())
                .titulo(dto.getTitulo())
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo())
                .estado(dto.getEstado())
                .build();

        return mapToDTO(repository.save(n));
    }

    @Override
    public NotificacionResponseDTO actualizar(Long id, NotificacionRequestDTO dto) {

        Notificacion n = repository.findById(id)
                .orElseThrow(() ->
                        new NotificacionNotFoundException("Notificación no encontrada"));

        n.setDestinatario(dto.getDestinatario());
        n.setTitulo(dto.getTitulo());
        n.setMensaje(dto.getMensaje());
        n.setTipo(dto.getTipo());
        n.setEstado(dto.getEstado());

        return mapToDTO(repository.save(n));
    }

    @Override
    public void eliminar(Long id) {

        Notificacion n = repository.findById(id)
                .orElseThrow(() ->
                        new NotificacionNotFoundException("Notificación no encontrada"));

        repository.delete(n);
    }

    private NotificacionResponseDTO mapToDTO(Notificacion n) {

        return NotificacionResponseDTO.builder()
                .id(n.getId())
                .destinatario(n.getDestinatario())
                .titulo(n.getTitulo())
                .mensaje(n.getMensaje())
                .tipo(n.getTipo())
                .estado(n.getEstado())
                .build();
    }
}