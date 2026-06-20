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

        when(repository.save(any(Pago.class)))
                .thenAnswer(invocation -> {
                    Pago pago = invocation.getArgument(0);
                    pago.setId(1L);
                    return pago;
                });

        PagoResponseDTO response = service.guardar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Consulta médica", response.getConcepto());
        assertEquals(new BigDecimal("25000"), response.getMonto());
        assertEquals("TARJETA", response.getMetodoPago());
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals(LocalDate.now(), response.getFechaPago());

        verify(pacienteClient).buscarPaciente(1L);
        verify(repository).save(any(Pago.class));
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMontoEsCero() {

        request.setMonto(BigDecimal.ZERO);

        ReglaNegocioException exception = assertThrows(
                ReglaNegocioException.class,
                () -> service.guardar(request));

        assertEquals(
                "El monto debe ser mayor a cero",
                exception.getMessage());

        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Pago.class));
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMontoEsNegativo() {

        request.setMonto(new BigDecimal("-1000"));

        ReglaNegocioException exception = assertThrows(
                ReglaNegocioException.class,
                () -> service.guardar(request));

        assertEquals(
                "El monto debe ser mayor a cero",
                exception.getMessage());

        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Pago.class));
    }
}