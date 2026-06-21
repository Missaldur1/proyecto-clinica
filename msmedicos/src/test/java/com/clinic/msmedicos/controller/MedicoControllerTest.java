package com.clinic.msmedicos.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.service.MedicoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class MedicoControllerTest {

    @Mock
    private MedicoService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private MedicoRequestDTO request;

    private MedicoResponseDTO response;

    @BeforeEach
    void setUp() {
        MedicoController controller = new MedicoController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();

        request = new MedicoRequestDTO();
        request.setRut("22222222-2");
        request.setNombre("Ana");
        request.setApellido("Pérez");
        request.setEspecialidad("Cardiología");
        request.setCorreo("ana.perez@clinica.cl");
        request.setTelefono("912345678");
        request.setDisponible(true);

        response = new MedicoResponseDTO();
        response.setId(1L);
        response.setRut("22222222-2");
        response.setNombre("Ana");
        response.setApellido("Pérez");
        response.setEspecialidad("Cardiología");
        response.setCorreo("ana.perez@clinica.cl");
        response.setTelefono("912345678");
        response.setDisponible(true);
    }

    @Test
    void guardar_debeRetornarStatus201YMedicoCreado() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.guardar(any(MedicoRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("22222222-2"))
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.especialidad").value("Cardiología"))
                .andExpect(jsonPath("$.correo").value("ana.perez@clinica.cl"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.disponible").value(true));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).guardar(any(MedicoRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/medicos.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeMedicos() throws Exception {

        // ARRANGE: preparar lista simulada de médicos
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/medicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rut").value("22222222-2"))
                .andExpect(jsonPath("$[0].nombre").value("Ana"))
                .andExpect(jsonPath("$[0].apellido").value("Pérez"))
                .andExpect(jsonPath("$[0].especialidad").value("Cardiología"))
                .andExpect(jsonPath("$[0].correo").value("ana.perez@clinica.cl"))
                .andExpect(jsonPath("$[0].telefono").value("912345678"))
                .andExpect(jsonPath("$[0].disponible").value(true));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/medicos falla al listar médicos.
    }

    @Test
    void buscar_debeRetornarStatus200YMedico() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/medicos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("22222222-2"))
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.especialidad").value("Cardiología"))
                .andExpect(jsonPath("$.correo").value("ana.perez@clinica.cl"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.disponible").value(true));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca un médico existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YMedicoActualizado() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(MedicoRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/medicos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("22222222-2"))
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.especialidad").value("Cardiología"))
                .andExpect(jsonPath("$.correo").value("ana.perez@clinica.cl"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.disponible").value(true));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(MedicoRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/medicos/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}