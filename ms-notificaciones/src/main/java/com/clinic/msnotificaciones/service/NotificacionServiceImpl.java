package com.clinic.msnotificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;
import com.clinic.msnotificaciones.exception.NotificacionNotFoundException;
import com.clinic.msnotificaciones.mapper.NotificacionMapper;
import com.clinic.msnotificaciones.model.Notificacion;
import com.clinic.msnotificaciones.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;

    @Override
    public List<NotificacionResponseDTO> listar() {
        log.info("Listando notificaciones");
        return repository.findAll()
                .stream()
                .map(NotificacionMapper::toDTO)
                .toList();
    }

    @Override
    public NotificacionResponseDTO buscarPorId(Long id) {

        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new NotificacionNotFoundException("Notificación no encontrada"));

        log.info("Buscando notificación con id: {}", id);
        return NotificacionMapper.toDTO(n);
    }

    @Override
    public NotificacionResponseDTO guardar(NotificacionRequestDTO dto) {

        log.info("Creando notificación para: {}", dto.getDestinatario());

        Notificacion n = NotificacionMapper.toEntity(dto);

        Notificacion guardada = repository.save(n);

        log.info("Notificación creada con id: {}",
                guardada.getId());

        return NotificacionMapper.toDTO(guardada);
    }

    @Override
    public NotificacionResponseDTO actualizar(Long id, NotificacionRequestDTO dto) {

        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new NotificacionNotFoundException("Notificación no encontrada"));

        log.info("Actualizando notificación con id: {}", id);

        NotificacionMapper.updateEntity(n, dto);

        Notificacion actualizada = repository.save(n);

        log.info("Notificación actualizada correctamente");

        return NotificacionMapper.toDTO(actualizada);
    }

    @Override
    public void eliminar(Long id) {

        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new NotificacionNotFoundException("Notificación no encontrada"));

        log.info("Eliminando notificación con id: {}", id);

        repository.delete(n);

        log.info("Notificación eliminada correctamente");
    }
}