package com.clinic.msnotificaciones.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;
import com.clinic.msnotificaciones.exception.NotificacionNotFoundException;
import com.clinic.msnotificaciones.model.Notificacion;
import com.clinic.msnotificaciones.repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionServiceImpl service;

    private NotificacionRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new NotificacionRequestDTO();
        request.setDestinatario("paciente@test.cl");
        request.setTitulo("Confirmación de reserva");
        request.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        request.setTipo("EMAIL");
        request.setEstado("PENDIENTE");
    }

    @Test
    void guardar_debeCrearNotificacionCuandoDatosSonValidos() {

        when(repository.save(any(Notificacion.class)))
                .thenAnswer(invocation -> {
                    Notificacion notificacion = invocation.getArgument(0);
                    notificacion.setId(1L);
                    return notificacion;
                });

        NotificacionResponseDTO response = service.guardar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.cl", response.getDestinatario());
        assertEquals("Confirmación de reserva", response.getTitulo());
        assertEquals("Su reserva médica ha sido confirmada correctamente.", response.getMensaje());
        assertEquals("EMAIL", response.getTipo());
        assertEquals("PENDIENTE", response.getEstado());

        verify(repository).save(any(Notificacion.class));
    }

    @Test
    void listar_debeRetornarNotificaciones() {

        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("paciente@test.cl");
        notificacion.setTitulo("Confirmación de reserva");
        notificacion.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        notificacion.setTipo("EMAIL");
        notificacion.setEstado("PENDIENTE");

        when(repository.findAll())
                .thenReturn(List.of(notificacion));

        List<NotificacionResponseDTO> response = service.listar();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("paciente@test.cl", response.get(0).getDestinatario());
        assertEquals("EMAIL", response.get(0).getTipo());

        verify(repository).findAll();
    }

    @Test
    void buscarPorId_debeRetornarNotificacionCuandoExiste() {

        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("paciente@test.cl");
        notificacion.setTitulo("Confirmación de reserva");
        notificacion.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        notificacion.setTipo("EMAIL");
        notificacion.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        NotificacionResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.cl", response.getDestinatario());
        assertEquals("Confirmación de reserva", response.getTitulo());
        assertEquals("EMAIL", response.getTipo());
        assertEquals("PENDIENTE", response.getEstado());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNotificacionNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        NotificacionNotFoundException exception = assertThrows(
                NotificacionNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Notificación no encontrada", exception.getMessage());

        verify(repository).findById(99L);
    }

    @Test
    void actualizar_debeActualizarNotificacionCuandoExiste() {

        Notificacion existente = new Notificacion();
        existente.setId(1L);
        existente.setDestinatario("antiguo@test.cl");
        existente.setTitulo("Título anterior");
        existente.setMensaje("Mensaje anterior");
        existente.setTipo("EMAIL");
        existente.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(any(Notificacion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NotificacionResponseDTO response = service.actualizar(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.cl", response.getDestinatario());
        assertEquals("Confirmación de reserva", response.getTitulo());
        assertEquals("Su reserva médica ha sido confirmada correctamente.", response.getMensaje());
        assertEquals("EMAIL", response.getTipo());
        assertEquals("PENDIENTE", response.getEstado());

        verify(repository).findById(1L);
        verify(repository).save(any(Notificacion.class));
    }

    @Test
    void eliminar_debeEliminarNotificacionCuandoExiste() {

        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("paciente@test.cl");
        notificacion.setTitulo("Confirmación de reserva");
        notificacion.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        notificacion.setTipo("EMAIL");
        notificacion.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        service.eliminar(1L);

        verify(repository).findById(1L);
        verify(repository).delete(notificacion);
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoNotificacionNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        NotificacionNotFoundException exception = assertThrows(
                NotificacionNotFoundException.class,
                () -> service.eliminar(99L));

        assertEquals("Notificación no encontrada", exception.getMessage());

        verify(repository).findById(99L);
        verify(repository, never()).delete(any(Notificacion.class));
    }
}