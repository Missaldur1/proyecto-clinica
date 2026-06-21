package com.clinic.msreservas.service;

import java.time.LocalDate;
import java.time.LocalTime;

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

import com.clinic.msreservas.client.MedicoClient;
import com.clinic.msreservas.client.PacienteClient;
import com.clinic.msreservas.dto.ReservaRequestDTO;
import com.clinic.msreservas.dto.ReservaResponseDTO;
import com.clinic.msreservas.exception.FechaReservaInvalidaException;
import com.clinic.msreservas.exception.HorarioReservaInvalidoException;
import com.clinic.msreservas.exception.ReservaDuplicadaException;
import com.clinic.msreservas.model.Reserva;
import com.clinic.msreservas.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ReservaRepository repository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private MedicoClient medicoClient;

    @InjectMocks
    private ReservaServiceImpl service;

    private ReservaRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new ReservaRequestDTO();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setFecha(LocalDate.now().plusDays(1));
        request.setHora(LocalTime.of(10, 30));
        request.setEstado("PENDIENTE");
    }

    @Test
    void crear_debeCrearReservaCuandoDatosSonValidos() {

        // ARRANGE: preparar datos válidos y configurar mocks
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        when(repository.existsByMedicoIdAndFechaAndHora(
                2L,
                request.getFecha(),
                request.getHora()))
                .thenReturn(false);

        when(repository.save(any(Reserva.class)))
                .thenAnswer(invocation -> {
                    Reserva reserva = invocation.getArgument(0);
                    reserva.setId(1L);
                    return reserva;
                });

        // ACT: ejecutar el método real del service
        ReservaResponseDTO response = service.crear(request);

        // ASSERT: verificar que la reserva creada tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(2L, response.getMedicoId());
        assertEquals(request.getFecha(), response.getFecha());
        assertEquals(request.getHora(), response.getHora());
        assertEquals("PENDIENTE", response.getEstado());

        // VERIFY: comprobar llamadas a Feign Clients y Repository
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(repository).existsByMedicoIdAndFechaAndHora(
                2L,
                request.getFecha(),
                request.getHora());
        verify(repository).save(any(Reserva.class));

        // Caso hipotético QA:
        // Si se esperaba crear la reserva y se obtiene una excepción,
        // QA debe reportar que una validación está bloqueando reservas válidas.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoFechaEsPasada() {

        // ARRANGE: preparar una reserva con fecha pasada
        request.setFecha(LocalDate.now().minusDays(1));

        // ACT + ASSERT: ejecutar el método y verificar que lance excepción
        FechaReservaInvalidaException exception = assertThrows(
                FechaReservaInvalidaException.class,
                () -> service.crear(request));

        assertEquals(
                "No se pueden crear reservas en fechas pasadas",
                exception.getMessage());

        // VERIFY: comprobar que no se consultaron dependencias externas ni se guardó
        verify(pacienteClient, never()).buscarPaciente(1L);
        verify(medicoClient, never()).buscarMedico(2L);
        verify(repository, never()).save(any(Reserva.class));

        // Caso hipotético QA:
        // Si el sistema permite guardar reservas con fecha pasada,
        // QA debe reportar una falla en la regla de negocio de fecha.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoHorarioEstaFueraDeRango() {

        // ARRANGE: preparar una reserva con horario fuera del rango permitido
        request.setHora(LocalTime.of(20, 30));

        // ACT + ASSERT: ejecutar el método y verificar que lance excepción
        HorarioReservaInvalidoException exception = assertThrows(
                HorarioReservaInvalidoException.class,
                () -> service.crear(request));

        assertEquals(
                "Horario fuera del rango de atención",
                exception.getMessage());

        // VERIFY: comprobar que no se consultaron dependencias externas ni se guardó
        verify(pacienteClient, never()).buscarPaciente(1L);
        verify(medicoClient, never()).buscarMedico(2L);
        verify(repository, never()).save(any(Reserva.class));

        // Caso hipotético QA:
        // Si el sistema permite reservar fuera del horario de atención,
        // QA debe reportar que la validación horaria no está funcionando.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoReservaEstaDuplicada() {

        // ARRANGE: preparar mocks para simular una reserva ya existente
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        when(repository.existsByMedicoIdAndFechaAndHora(
                2L,
                request.getFecha(),
                request.getHora()))
                .thenReturn(true);

        // ACT + ASSERT: ejecutar el método y verificar que lance excepción
        ReservaDuplicadaException exception = assertThrows(
                ReservaDuplicadaException.class,
                () -> service.crear(request));

        assertEquals(
                "Horario ya reservado",
                exception.getMessage());

        // VERIFY: comprobar que se validaron paciente, médico y duplicidad,
        // pero no se guardó la reserva
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(repository).existsByMedicoIdAndFechaAndHora(
                2L,
                request.getFecha(),
                request.getHora());
        verify(repository, never()).save(any(Reserva.class));

        // Caso hipotético QA:
        // Si el sistema permite crear dos reservas con el mismo médico,
        // fecha y hora, QA debe reportar falla en la validación de duplicidad.
    }
}