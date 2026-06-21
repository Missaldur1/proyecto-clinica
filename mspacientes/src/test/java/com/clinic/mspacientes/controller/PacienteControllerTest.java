package com.clinic.mspacientes.controller;

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

import com.clinic.mspacientes.dto.PacienteRequestDTO;
import com.clinic.mspacientes.dto.PacienteResponseDTO;
import com.clinic.mspacientes.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private PacienteRequestDTO request;

    private PacienteResponseDTO response;

    @BeforeEach
    void setUp() {
        PacienteController controller = new PacienteController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();

        request = new PacienteRequestDTO();
        request.setRut("11111111-1");
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEdad(30);
        request.setPrevision("FONASA");
        request.setTelefono("912345678");
        request.setEmail("juan.perez@correo.cl");

        response = new PacienteResponseDTO();
        response.setId(1L);
        response.setRut("11111111-1");
        response.setNombre("Juan");
        response.setApellido("Pérez");
        response.setEdad(30);
        response.setPrevision("FONASA");
        response.setTelefono("912345678");
        response.setEmail("juan.perez@correo.cl");
    }

    @Test
    void crearPaciente_debeRetornarStatus201YPacienteCreado() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.crearPaciente(any(PacienteRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("11111111-1"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.prevision").value("FONASA"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.email").value("juan.perez@correo.cl"));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).crearPaciente(any(PacienteRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/pacientes.
    }

    @Test
    void listarPacientes_debeRetornarStatus200YListaDePacientes() throws Exception {

        // ARRANGE: preparar lista simulada de pacientes
        when(service.listarPacientes())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rut").value("11111111-1"))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellido").value("Pérez"))
                .andExpect(jsonPath("$[0].edad").value(30))
                .andExpect(jsonPath("$[0].prevision").value("FONASA"))
                .andExpect(jsonPath("$[0].telefono").value("912345678"))
                .andExpect(jsonPath("$[0].email").value("juan.perez@correo.cl"));

        // VERIFY: comprobar llamada al service
        verify(service).listarPacientes();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/pacientes falla al listar pacientes.
    }

    @Test
    void buscarPorId_debeRetornarStatus200YPaciente() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/pacientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("11111111-1"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.prevision").value("FONASA"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.email").value("juan.perez@correo.cl"));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca un paciente existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void buscarPorRut_debeRetornarStatus200YPaciente() throws Exception {

        // ARRANGE: simular búsqueda exitosa por RUT
        when(service.buscarPorRut("11111111-1"))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por RUT y validar respuesta
        mockMvc.perform(get("/api/pacientes/rut/{rut}", "11111111-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("11111111-1"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.prevision").value("FONASA"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.email").value("juan.perez@correo.cl"));

        // VERIFY: comprobar que se consultó el RUT correcto
        verify(service).buscarPorRut("11111111-1");

        // Caso hipotético QA:
        // Si se busca un RUT existente y el sistema responde error,
        // QA debe reportar falla en el endpoint GET /api/pacientes/rut/{rut}.
    }

    @Test
    void actualizarPaciente_debeRetornarStatus200YPacienteActualizado() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizarPaciente(eq(1L), any(PacienteRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/pacientes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rut").value("11111111-1"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.prevision").value("FONASA"))
                .andExpect(jsonPath("$.telefono").value("912345678"))
                .andExpect(jsonPath("$.email").value("juan.perez@correo.cl"));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizarPaciente(eq(1L), any(PacienteRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminarPaciente_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/pacientes/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminarPaciente(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}