package com.clinic.msrecetas.controller;

import java.time.LocalDate;
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

import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.service.RecetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class RecetaControllerTest {

    @Mock
    private RecetaService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RecetaRequestDTO request;

    private RecetaResponseDTO response;

    @BeforeEach
    void setUp() {
        RecetaController controller = new RecetaController(service);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        request = new RecetaRequestDTO();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setMedicamento("Paracetamol");
        request.setDosis("500mg cada 8 horas");
        request.setIndicaciones("Tomar después de las comidas");
        request.setActiva(true);

        response = new RecetaResponseDTO();
        response.setId(1L);
        response.setPacienteId(1L);
        response.setMedicoId(2L);
        response.setMedicamento("Paracetamol");
        response.setDosis("500mg cada 8 horas");
        response.setIndicaciones("Tomar después de las comidas");
        response.setFechaEmision(LocalDate.of(2026, 6, 25));
        response.setActiva(true);
    }

    @Test
    void guardar_debeRetornarStatus201YRecetaCreada() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.guardar(any(RecetaRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.medicamento").value("Paracetamol"))
                .andExpect(jsonPath("$.dosis").value("500mg cada 8 horas"))
                .andExpect(jsonPath("$.indicaciones").value("Tomar después de las comidas"))
                .andExpect(jsonPath("$.fechaEmision").value("2026-06-25"))
                .andExpect(jsonPath("$.activa").value(true));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).guardar(any(RecetaRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/recetas.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeRecetas() throws Exception {

        // ARRANGE: preparar lista simulada de recetas
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pacienteId").value(1))
                .andExpect(jsonPath("$[0].medicoId").value(2))
                .andExpect(jsonPath("$[0].medicamento").value("Paracetamol"))
                .andExpect(jsonPath("$[0].dosis").value("500mg cada 8 horas"))
                .andExpect(jsonPath("$[0].indicaciones").value("Tomar después de las comidas"))
                .andExpect(jsonPath("$[0].fechaEmision").value("2026-06-25"))
                .andExpect(jsonPath("$[0].activa").value(true));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/recetas falla al listar recetas.
    }

    @Test
    void buscar_debeRetornarStatus200YReceta() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/recetas/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.medicamento").value("Paracetamol"))
                .andExpect(jsonPath("$.dosis").value("500mg cada 8 horas"))
                .andExpect(jsonPath("$.indicaciones").value("Tomar después de las comidas"))
                .andExpect(jsonPath("$.fechaEmision").value("2026-06-25"))
                .andExpect(jsonPath("$.activa").value(true));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca una receta existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YRecetaActualizada() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(RecetaRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/recetas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.medicamento").value("Paracetamol"))
                .andExpect(jsonPath("$.dosis").value("500mg cada 8 horas"))
                .andExpect(jsonPath("$.indicaciones").value("Tomar después de las comidas"))
                .andExpect(jsonPath("$.fechaEmision").value("2026-06-25"))
                .andExpect(jsonPath("$.activa").value(true));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(RecetaRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/recetas/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}