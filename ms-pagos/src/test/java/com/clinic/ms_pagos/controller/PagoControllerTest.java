package com.clinic.ms_pagos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;
import com.clinic.ms_pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class PagoControllerTest {

    @Mock
    private PagoService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private PagoRequestDTO request;

    private PagoResponseDTO response;

    @BeforeEach
    void setUp() {
        PagoController controller = new PagoController(service);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        request = new PagoRequestDTO();
        request.setPacienteId(1L);
        request.setConcepto("Consulta médica");
        request.setMonto(new BigDecimal("25000"));
        request.setMetodoPago("TARJETA");

        response = new PagoResponseDTO();
        response.setId(1L);
        response.setPacienteId(1L);
        response.setConcepto("Consulta médica");
        response.setMonto(new BigDecimal("25000"));
        response.setMetodoPago("TARJETA");
        response.setEstado("PENDIENTE");
        response.setFechaPago(LocalDate.of(2026, 6, 25));
    }

    @Test
    void guardar_debeRetornarStatus201YPagoCreado() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.guardar(any(PagoRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.concepto").value("Consulta médica"))
                .andExpect(jsonPath("$.monto").value(25000))
                .andExpect(jsonPath("$.metodoPago").value("TARJETA"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.fechaPago").value("2026-06-25"));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).guardar(any(PagoRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/pagos.
    }

    @Test
    void listar_debeRetornarStatus200YListaDePagos() throws Exception {

        // ARRANGE: preparar lista simulada de pagos
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pacienteId").value(1))
                .andExpect(jsonPath("$[0].concepto").value("Consulta médica"))
                .andExpect(jsonPath("$[0].monto").value(25000))
                .andExpect(jsonPath("$[0].metodoPago").value("TARJETA"))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"))
                .andExpect(jsonPath("$[0].fechaPago").value("2026-06-25"));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/pagos falla al listar pagos.
    }

    @Test
    void buscar_debeRetornarStatus200YPago() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/pagos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.concepto").value("Consulta médica"))
                .andExpect(jsonPath("$.monto").value(25000))
                .andExpect(jsonPath("$.metodoPago").value("TARJETA"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.fechaPago").value("2026-06-25"));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca un pago existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YPagoActualizado() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(PagoRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/pagos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.concepto").value("Consulta médica"))
                .andExpect(jsonPath("$.monto").value(25000))
                .andExpect(jsonPath("$.metodoPago").value("TARJETA"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.fechaPago").value("2026-06-25"));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(PagoRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/pagos/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}