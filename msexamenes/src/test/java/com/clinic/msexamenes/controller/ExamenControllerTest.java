package com.clinic.msexamenes.controller;

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

import com.clinic.msexamenes.dto.ExamenRequestDTO;
import com.clinic.msexamenes.dto.ExamenResponseDTO;
import com.clinic.msexamenes.service.ExamenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class ExamenControllerTest {

    @Mock
    private ExamenService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ExamenRequestDTO request;

    private ExamenResponseDTO response;

    @BeforeEach
    void setUp() {
        ExamenController controller = new ExamenController(service);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        request = new ExamenRequestDTO();
        request.setPacienteId(1L);
        request.setTipoExamen("Hemograma");
        request.setFecha(LocalDate.of(2026, 6, 25));
        request.setResultado("Resultados dentro de rangos normales");
        request.setEstado("COMPLETADO");

        response = new ExamenResponseDTO();
        response.setId(1L);
        response.setPacienteId(1L);
        response.setTipoExamen("Hemograma");
        response.setFecha(LocalDate.of(2026, 6, 25));
        response.setResultado("Resultados dentro de rangos normales");
        response.setEstado("COMPLETADO");
    }

    @Test
    void crear_debeRetornarStatus201YExamenCreado() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.crear(any(ExamenRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/examenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.tipoExamen").value("Hemograma"))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"))
                .andExpect(jsonPath("$.resultado").value("Resultados dentro de rangos normales"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).crear(any(ExamenRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/examenes.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeExamenes() throws Exception {

        // ARRANGE: preparar lista simulada de exámenes
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/examenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pacienteId").value(1))
                .andExpect(jsonPath("$[0].tipoExamen").value("Hemograma"))
                .andExpect(jsonPath("$[0].fecha").value("2026-06-25"))
                .andExpect(jsonPath("$[0].resultado").value("Resultados dentro de rangos normales"))
                .andExpect(jsonPath("$[0].estado").value("COMPLETADO"));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/examenes falla al listar exámenes.
    }

    @Test
    void buscar_debeRetornarStatus200YExamen() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/examenes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.tipoExamen").value("Hemograma"))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"))
                .andExpect(jsonPath("$.resultado").value("Resultados dentro de rangos normales"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca un examen existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YExamenActualizado() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(ExamenRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/examenes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.tipoExamen").value("Hemograma"))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"))
                .andExpect(jsonPath("$.resultado").value("Resultados dentro de rangos normales"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(ExamenRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/examenes/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}