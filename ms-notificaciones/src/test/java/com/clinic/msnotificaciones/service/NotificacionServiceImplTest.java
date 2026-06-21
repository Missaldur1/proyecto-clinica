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

        // ARRANGE: preparar datos válidos y simular guardado en repositorio
        when(repository.save(any(Notificacion.class)))
                .thenAnswer(invocation -> {
                    Notificacion notificacion = invocation.getArgument(0);
                    notificacion.setId(1L);
                    return notificacion;
                });

        // ACT: ejecutar el método real del service
        NotificacionResponseDTO response = service.guardar(request);

        // ASSERT: verificar que la notificación creada tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.cl", response.getDestinatario());
        assertEquals("Confirmación de reserva", response.getTitulo());
        assertEquals("Su reserva médica ha sido confirmada correctamente.", response.getMensaje());
        assertEquals("EMAIL", response.getTipo());
        assertEquals("PENDIENTE", response.getEstado());

        // VERIFY: comprobar que el repository guardó la notificación
        verify(repository).save(any(Notificacion.class));

        // Caso hipotético QA:
        // Si se esperaba crear una notificación válida y el sistema responde error,
        // QA debe reportar una falla en el guardado de notificaciones.
    }

    @Test
    void listar_debeRetornarNotificaciones() {

        // ARRANGE: preparar una lista simulada de notificaciones
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("paciente@test.cl");
        notificacion.setTitulo("Confirmación de reserva");
        notificacion.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        notificacion.setTipo("EMAIL");
        notificacion.setEstado("PENDIENTE");

        when(repository.findAll())
                .thenReturn(List.of(notificacion));

        // ACT: ejecutar listado de notificaciones
        List<NotificacionResponseDTO> response = service.listar();

        // ASSERT: verificar que la lista tenga la notificación esperada
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("paciente@test.cl", response.get(0).getDestinatario());
        assertEquals("EMAIL", response.get(0).getTipo());

        // VERIFY: comprobar que se consultó el repositorio
        verify(repository).findAll();

        // Caso hipotético QA:
        // Si existen notificaciones registradas y el listado retorna vacío,
        // QA debe reportar una falla en el método listar.
    }

    @Test
    void buscarPorId_debeRetornarNotificacionCuandoExiste() {

        // ARRANGE: preparar una notificación existente en el repositorio simulado
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("paciente@test.cl");
        notificacion.setTitulo("Confirmación de reserva");
        notificacion.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        notificacion.setTipo("EMAIL");
        notificacion.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        // ACT: ejecutar búsqueda por ID
        NotificacionResponseDTO response = service.buscarPorId(1L);

        // ASSERT: verificar que la respuesta corresponda a la notificación esperada
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.cl", response.getDestinatario());
        assertEquals("Confirmación de reserva", response.getTitulo());
        assertEquals("EMAIL", response.getTipo());
        assertEquals("PENDIENTE", response.getEstado());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(1L);

        // Caso hipotético QA:
        // Si se busca una notificación existente y el sistema responde no encontrada,
        // QA debe reportar una falla en la búsqueda por ID.
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNotificacionNoExiste() {

        // ARRANGE: simular que no existe notificación con ID 99
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar búsqueda y verificar excepción
        NotificacionNotFoundException exception = assertThrows(
                NotificacionNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals(
                "Notificación no encontrada",
                exception.getMessage());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(99L);

        // Caso hipotético QA:
        // Si se busca una notificación inexistente y el sistema responde 200 OK,
        // QA debe reportar que no se está manejando correctamente el caso no encontrado.
    }

    @Test
    void actualizar_debeActualizarNotificacionCuandoExiste() {

        // ARRANGE: preparar una notificación existente y datos nuevos
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

        // ACT: ejecutar actualización
        NotificacionResponseDTO response = service.actualizar(1L, request);

        // ASSERT: verificar que los datos fueron actualizados correctamente
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.cl", response.getDestinatario());
        assertEquals("Confirmación de reserva", response.getTitulo());
        assertEquals("Su reserva médica ha sido confirmada correctamente.", response.getMensaje());
        assertEquals("EMAIL", response.getTipo());
        assertEquals("PENDIENTE", response.getEstado());

        // VERIFY: comprobar búsqueda por ID y guardado de cambios
        verify(repository).findById(1L);
        verify(repository).save(any(Notificacion.class));

        // Caso hipotético QA:
        // Si se actualiza una notificación y los datos antiguos permanecen,
        // QA debe reportar una falla en el método actualizar.
    }

    @Test
    void eliminar_debeEliminarNotificacionCuandoExiste() {

        // ARRANGE: preparar una notificación existente
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("paciente@test.cl");
        notificacion.setTitulo("Confirmación de reserva");
        notificacion.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        notificacion.setTipo("EMAIL");
        notificacion.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        // ACT: ejecutar eliminación
        service.eliminar(1L);

        // VERIFY: comprobar que se buscó y eliminó la notificación
        verify(repository).findById(1L);
        verify(repository).delete(notificacion);

        // Caso hipotético QA:
        // Si se elimina una notificación existente y sigue apareciendo en el listado,
        // QA debe reportar que la eliminación no se está aplicando correctamente.
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoNotificacionNoExiste() {

        // ARRANGE: simular que no existe notificación con ID 99
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar eliminación y verificar excepción
        NotificacionNotFoundException exception = assertThrows(
                NotificacionNotFoundException.class,
                () -> service.eliminar(99L));

        assertEquals(
                "Notificación no encontrada",
                exception.getMessage());

        // VERIFY: comprobar que se consultó por ID, pero no se eliminó nada
        verify(repository).findById(99L);
        verify(repository, never()).delete(any(Notificacion.class));

        // Caso hipotético QA:
        // Si se intenta eliminar una notificación inexistente y el sistema responde OK,
        // QA debe reportar que no se está manejando correctamente el caso no encontrado.
    }
}