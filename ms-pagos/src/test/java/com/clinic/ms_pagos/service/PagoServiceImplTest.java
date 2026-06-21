package com.clinic.ms_pagos.service;

import java.math.BigDecimal;
import java.time.LocalDate;

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

import com.clinic.ms_pagos.client.PacienteClient;
import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;
import com.clinic.ms_pagos.exception.ReglaNegocioException;
import com.clinic.ms_pagos.model.Pago;
import com.clinic.ms_pagos.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private PacienteClient pacienteClient;

    @InjectMocks
    private PagoServiceImpl service;

    private PagoRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new PagoRequestDTO();
        request.setPacienteId(1L);
        request.setConcepto("Consulta médica");
        request.setMonto(new BigDecimal("25000"));
        request.setMetodoPago("TARJETA");
    }

    @Test
    void guardar_debeCrearPagoCuandoDatosSonValidos() {

        // ARRANGE: preparar datos válidos y configurar mocks
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(repository.save(any(Pago.class)))
                .thenAnswer(invocation -> {
                    Pago pago = invocation.getArgument(0);
                    pago.setId(1L);
                    return pago;
                });

        // ACT: ejecutar el método real del service
        PagoResponseDTO response = service.guardar(request);

        // ASSERT: verificar que la respuesta tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Consulta médica", response.getConcepto());
        assertEquals(new BigDecimal("25000"), response.getMonto());
        assertEquals("TARJETA", response.getMetodoPago());
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals(LocalDate.now(), response.getFechaPago());

        // VERIFY: comprobar que se consultó el paciente y se guardó el pago
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository).save(any(Pago.class));

        // Caso hipotético QA:
        // Si se esperaba estado PENDIENTE y se obtiene null,
        // QA debe reportar que el service no está asignando correctamente
        // el estado inicial al crear un pago.
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMontoEsCero() {

        // ARRANGE: preparar un request con monto inválido igual a cero
        request.setMonto(BigDecimal.ZERO);

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        // ACT + ASSERT: ejecutar el método y verificar que lance excepción
        ReglaNegocioException exception = assertThrows(
                ReglaNegocioException.class,
                () -> service.guardar(request));

        assertEquals(
                "El monto debe ser mayor a cero",
                exception.getMessage());

        // VERIFY: comprobar que se validó el paciente, pero no se guardó el pago
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Pago.class));

        // Caso hipotético QA:
        // Si el sistema permite guardar un pago con monto 0,
        // QA debe reportar una falla en la regla de negocio del monto mínimo.
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMontoEsNegativo() {

        // ARRANGE: preparar un request con monto inválido negativo
        request.setMonto(new BigDecimal("-1000"));

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        // ACT + ASSERT: ejecutar el método y verificar que lance excepción
        ReglaNegocioException exception = assertThrows(
                ReglaNegocioException.class,
                () -> service.guardar(request));

        assertEquals(
                "El monto debe ser mayor a cero",
                exception.getMessage());

        // VERIFY: comprobar que se validó el paciente, pero no se guardó el pago
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Pago.class));

        // Caso hipotético QA:
        // Si el sistema permite guardar un pago con monto negativo,
        // QA debe reportar que no se está aplicando la validación de monto.
    }
}