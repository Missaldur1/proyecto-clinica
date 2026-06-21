package com.clinic.msfarmacia.controller;

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

import com.clinic.msfarmacia.dto.MedicamentoRequestDTO;
import com.clinic.msfarmacia.dto.MedicamentoResponseDTO;
import com.clinic.msfarmacia.service.MedicamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class MedicamentoControllerTest {

    @Mock
    private MedicamentoService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private MedicamentoRequestDTO request;

    private MedicamentoResponseDTO response;

    @BeforeEach
    void setUp() {
        MedicamentoController controller = new MedicamentoController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();

        request = new MedicamentoRequestDTO();
        request.setNombre("Paracetamol");
        request.setDescripcion("Analgésico y antipirético");
        request.setLaboratorio("Laboratorio Chile");
        request.setPrecio(2500.0);
        request.setStock(100);
        request.setDisponible(true);

        response = new MedicamentoResponseDTO();
        response.setId(1L);
        response.setNombre("Paracetamol");
        response.setDescripcion("Analgésico y antipirético");
        response.setLaboratorio("Laboratorio Chile");
        response.setPrecio(2500.0);
        response.setStock(100);
        response.setDisponible(true);
    }

    @Test
    void guardar_debeRetornarStatus201YMedicamentoCreado() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.guardar(any(MedicamentoRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Paracetamol"))
                .andExpect(jsonPath("$.descripcion").value("Analgésico y antipirético"))
                .andExpect(jsonPath("$.laboratorio").value("Laboratorio Chile"))
                .andExpect(jsonPath("$.precio").value(2500.0))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.disponible").value(true));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).guardar(any(MedicamentoRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/medicamentos.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeMedicamentos() throws Exception {

        // ARRANGE: preparar lista simulada de medicamentos
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Paracetamol"))
                .andExpect(jsonPath("$[0].descripcion").value("Analgésico y antipirético"))
                .andExpect(jsonPath("$[0].laboratorio").value("Laboratorio Chile"))
                .andExpect(jsonPath("$[0].precio").value(2500.0))
                .andExpect(jsonPath("$[0].stock").value(100))
                .andExpect(jsonPath("$[0].disponible").value(true));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/medicamentos falla al listar medicamentos.
    }

    @Test
    void buscar_debeRetornarStatus200YMedicamento() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/medicamentos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Paracetamol"))
                .andExpect(jsonPath("$.descripcion").value("Analgésico y antipirético"))
                .andExpect(jsonPath("$.laboratorio").value("Laboratorio Chile"))
                .andExpect(jsonPath("$.precio").value(2500.0))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.disponible").value(true));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca un medicamento existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YMedicamentoActualizado() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(MedicamentoRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/medicamentos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Paracetamol"))
                .andExpect(jsonPath("$.descripcion").value("Analgésico y antipirético"))
                .andExpect(jsonPath("$.laboratorio").value("Laboratorio Chile"))
                .andExpect(jsonPath("$.precio").value(2500.0))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.disponible").value(true));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(MedicamentoRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/medicamentos/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}
