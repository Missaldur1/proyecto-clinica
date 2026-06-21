package com.clinic.msnotificaciones.controller;

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

import com.clinic.msnotificaciones.dto.NotificacionRequestDTO;
import com.clinic.msnotificaciones.dto.NotificacionResponseDTO;
import com.clinic.msnotificaciones.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class NotificacionControllerTest {

    @Mock
    private NotificacionService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private NotificacionRequestDTO request;

    private NotificacionResponseDTO response;

    @BeforeEach
    void setUp() {
        NotificacionController controller = new NotificacionController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();

        request = new NotificacionRequestDTO();
        request.setDestinatario("paciente@test.cl");
        request.setTitulo("Confirmación de reserva");
        request.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        request.setTipo("EMAIL");
        request.setEstado("PENDIENTE");

        response = new NotificacionResponseDTO();
        response.setId(1L);
        response.setDestinatario("paciente@test.cl");
        response.setTitulo("Confirmación de reserva");
        response.setMensaje("Su reserva médica ha sido confirmada correctamente.");
        response.setTipo("EMAIL");
        response.setEstado("PENDIENTE");
    }

    @Test
    void guardar_debeRetornarStatus201YNotificacionCreada() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.guardar(any(NotificacionRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("paciente@test.cl"))
                .andExpect(jsonPath("$.titulo").value("Confirmación de reserva"))
                .andExpect(jsonPath("$.mensaje").value("Su reserva médica ha sido confirmada correctamente."))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).guardar(any(NotificacionRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/notificaciones.
    }

    @Test
    void listar_debeRetornarStatus200YListaDeNotificaciones() throws Exception {

        // ARRANGE: preparar lista simulada de notificaciones
        when(service.listar())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].destinatario").value("paciente@test.cl"))
                .andExpect(jsonPath("$[0].titulo").value("Confirmación de reserva"))
                .andExpect(jsonPath("$[0].mensaje").value("Su reserva médica ha sido confirmada correctamente."))
                .andExpect(jsonPath("$[0].tipo").value("EMAIL"))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));

        // VERIFY: comprobar llamada al service
        verify(service).listar();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/notificaciones falla al listar notificaciones.
    }

    @Test
    void buscar_debeRetornarStatus200YNotificacion() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/notificaciones/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("paciente@test.cl"))
                .andExpect(jsonPath("$.titulo").value("Confirmación de reserva"))
                .andExpect(jsonPath("$.mensaje").value("Su reserva médica ha sido confirmada correctamente."))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca una notificación existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizar_debeRetornarStatus200YNotificacionActualizada() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizar(eq(1L), any(NotificacionRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/notificaciones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("paciente@test.cl"))
                .andExpect(jsonPath("$.titulo").value("Confirmación de reserva"))
                .andExpect(jsonPath("$.mensaje").value("Su reserva médica ha sido confirmada correctamente."))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizar(eq(1L), any(NotificacionRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminar_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/notificaciones/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminar(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}