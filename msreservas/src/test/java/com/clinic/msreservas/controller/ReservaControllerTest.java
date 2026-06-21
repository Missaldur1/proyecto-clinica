package com.clinic.msreservas.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clinic.msreservas.dto.ReservaRequestDTO;
import com.clinic.msreservas.dto.ReservaResponseDTO;
import com.clinic.msreservas.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ReservaRequestDTO request;

    private ReservaResponseDTO response;

    @BeforeEach
    void setUp() {
        ReservaController controller = new ReservaController(service);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        request = new ReservaRequestDTO();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setFecha(LocalDate.of(2026, 6, 25));
        request.setHora(LocalTime.of(10, 30));
        request.setEstado("PENDIENTE");

        response = new ReservaResponseDTO();
        response.setId(1L);
        response.setPacienteId(1L);
        response.setMedicoId(2L);
        response.setFecha(LocalDate.of(2026, 6, 25));
        response.setHora(LocalTime.of(10, 30));
        response.setEstado("PENDIENTE");
    }

    @Test
    void crear_debeRetornarStatus201YReservaCreada() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.crear(any(ReservaRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"))
                .andExpect(jsonPath("$.hora").value("10:30:00"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).crear(any(ReservaRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/reservas.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeReservas() throws Exception {

        // ARRANGE: preparar lista simulada de reservas
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pacienteId").value(1))
                .andExpect(jsonPath("$[0].medicoId").value(2))
                .andExpect(jsonPath("$[0].fecha").value("2026-06-25"))
                .andExpect(jsonPath("$[0].hora").value("10:30:00"))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/reservas falla al listar reservas.
    }

    @Test
    void buscar_debeRetornarStatus200YReserva() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/reservas/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"))
                .andExpect(jsonPath("$.hora").value("10:30:00"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca una reserva existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YReservaActualizada() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(ReservaRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/reservas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"))
                .andExpect(jsonPath("$.hora").value("10:30:00"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(ReservaRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/reservas/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}