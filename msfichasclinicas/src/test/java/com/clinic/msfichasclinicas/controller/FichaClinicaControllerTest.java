package com.clinic.msfichasclinicas.controller;

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

import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.dto.FichaClinicaResponseDTO;
import com.clinic.msfichasclinicas.service.FichaClinicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class FichaClinicaControllerTest {

    @Mock
    private FichaClinicaService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private FichaClinicaRequestDTO request;

    private FichaClinicaResponseDTO response;

    @BeforeEach
    void setUp() {
        FichaClinicaController controller = new FichaClinicaController(service);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        request = new FichaClinicaRequestDTO();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setExamenId(3L);
        request.setDiagnostico("Paciente con evolución favorable");
        request.setTratamiento("Reposo e hidratación");
        request.setObservaciones("Sin observaciones adicionales");
        request.setFecha(LocalDate.of(2026, 6, 25));

        response = new FichaClinicaResponseDTO();
        response.setId(1L);
        response.setPacienteId(1L);
        response.setMedicoId(2L);
        response.setExamenId(3L);
        response.setDiagnostico("Paciente con evolución favorable");
        response.setTratamiento("Reposo e hidratación");
        response.setObservaciones("Sin observaciones adicionales");
        response.setFecha(LocalDate.of(2026, 6, 25));
    }

    @Test
    void crear_debeRetornarStatus201YFichaClinicaCreada() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.crear(any(FichaClinicaRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/fichas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.examenId").value(3))
                .andExpect(jsonPath("$.diagnostico").value("Paciente con evolución favorable"))
                .andExpect(jsonPath("$.tratamiento").value("Reposo e hidratación"))
                .andExpect(jsonPath("$.observaciones").value("Sin observaciones adicionales"))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).crear(any(FichaClinicaRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/fichas.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeFichasClinicas() throws Exception {

        // ARRANGE: preparar lista simulada de fichas clínicas
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/fichas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].pacienteId").value(1))
                .andExpect(jsonPath("$[0].medicoId").value(2))
                .andExpect(jsonPath("$[0].examenId").value(3))
                .andExpect(jsonPath("$[0].diagnostico").value("Paciente con evolución favorable"))
                .andExpect(jsonPath("$[0].tratamiento").value("Reposo e hidratación"))
                .andExpect(jsonPath("$[0].observaciones").value("Sin observaciones adicionales"))
                .andExpect(jsonPath("$[0].fecha").value("2026-06-25"));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/fichas falla al listar fichas clínicas.
    }

    @Test
    void buscar_debeRetornarStatus200YFichaClinica() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/fichas/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.examenId").value(3))
                .andExpect(jsonPath("$.diagnostico").value("Paciente con evolución favorable"))
                .andExpect(jsonPath("$.tratamiento").value("Reposo e hidratación"))
                .andExpect(jsonPath("$.observaciones").value("Sin observaciones adicionales"))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca una ficha clínica existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YFichaClinicaActualizada() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(FichaClinicaRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/fichas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(1))
                .andExpect(jsonPath("$.medicoId").value(2))
                .andExpect(jsonPath("$.examenId").value(3))
                .andExpect(jsonPath("$.diagnostico").value("Paciente con evolución favorable"))
                .andExpect(jsonPath("$.tratamiento").value("Reposo e hidratación"))
                .andExpect(jsonPath("$.observaciones").value("Sin observaciones adicionales"))
                .andExpect(jsonPath("$.fecha").value("2026-06-25"));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(FichaClinicaRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/fichas/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}