package com.clinic.msusuarios.controller;

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

import com.clinic.msusuarios.dto.UsuarioRequestDTO;
import com.clinic.msusuarios.dto.UsuarioResponseDTO;
import com.clinic.msusuarios.enums.Rol;
import com.clinic.msusuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private UsuarioRequestDTO request;

    private UsuarioResponseDTO response;

    @BeforeEach
    void setUp() {
        UsuarioController controller = new UsuarioController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        objectMapper = new ObjectMapper();

        request = new UsuarioRequestDTO();
        request.setNombre("Administrador");
        request.setEmail("admin@test.cl");
        request.setPassword("123456");
        request.setRol(Rol.ADMIN);
        request.setActivo(true);

        response = new UsuarioResponseDTO();
        response.setId(1L);
        response.setNombre("Administrador");
        response.setEmail("admin@test.cl");
        response.setRol(Rol.ADMIN);
        response.setActivo(true);
    }

    @Test
    void crearUsuario_debeRetornarStatus201YUsuarioCreado() throws Exception {

        // ARRANGE: preparar request válido y simular respuesta del service
        when(service.crearUsuario(any(UsuarioRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint POST y validar respuesta HTTP + JSON
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Administrador"))
                .andExpect(jsonPath("$.email").value("admin@test.cl"))
                .andExpect(jsonPath("$.rol").value("ADMIN"))
                .andExpect(jsonPath("$.activo").value(true));

        // VERIFY: comprobar que el controller llamó al método correcto del service
        verify(service).crearUsuario(any(UsuarioRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 201 Created y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint POST /api/usuarios.
    }

    @Test
    void listarUsuarios_debeRetornarStatus200YListaDeUsuarios() throws Exception {

        // ARRANGE: preparar lista simulada de usuarios
        when(service.listarUsuarios())
                .thenReturn(List.of(response));

        // ACT + ASSERT: ejecutar endpoint GET y validar lista JSON
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Administrador"))
                .andExpect(jsonPath("$[0].email").value("admin@test.cl"))
                .andExpect(jsonPath("$[0].rol").value("ADMIN"))
                .andExpect(jsonPath("$[0].activo").value(true));

        // VERIFY: comprobar llamada al service
        verify(service).listarUsuarios();

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 500,
        // QA debe reportar que el endpoint GET /api/usuarios falla al listar usuarios.
    }

    @Test
    void buscarPorId_debeRetornarStatus200YUsuario() throws Exception {

        // ARRANGE: simular búsqueda exitosa por ID
        when(service.buscarPorId(1L))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint GET por ID y validar respuesta
        mockMvc.perform(get("/api/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Administrador"))
                .andExpect(jsonPath("$.email").value("admin@test.cl"))
                .andExpect(jsonPath("$.rol").value("ADMIN"))
                .andExpect(jsonPath("$.activo").value(true));

        // VERIFY: comprobar que se consultó el ID correcto
        verify(service).buscarPorId(1L);

        // Caso hipotético QA:
        // Si se busca un usuario existente y se obtiene 404,
        // QA debe reportar falla en la búsqueda por ID.
    }

    @Test
    void actualizarUsuario_debeRetornarStatus200YUsuarioActualizado() throws Exception {

        // ARRANGE: preparar actualización simulada
        when(service.actualizarUsuario(eq(1L), any(UsuarioRequestDTO.class)))
                .thenReturn(response);

        // ACT + ASSERT: ejecutar endpoint PUT y validar respuesta
        mockMvc.perform(put("/api/usuarios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Administrador"))
                .andExpect(jsonPath("$.email").value("admin@test.cl"))
                .andExpect(jsonPath("$.rol").value("ADMIN"))
                .andExpect(jsonPath("$.activo").value(true));

        // VERIFY: comprobar que se actualizó el ID correcto
        verify(service).actualizarUsuario(eq(1L), any(UsuarioRequestDTO.class));

        // Caso hipotético QA:
        // Si se esperaba HTTP 200 OK y se obtiene HTTP 404,
        // QA debe revisar si el ID enviado existe o si el controller está enviando mal el parámetro.
    }

    @Test
    void eliminarUsuario_debeRetornarStatus204() throws Exception {

        // ARRANGE: no se necesita preparar respuesta porque el método es void

        // ACT + ASSERT: ejecutar endpoint DELETE y validar HTTP 204
        mockMvc.perform(delete("/api/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());

        // VERIFY: comprobar que se eliminó el ID correcto
        verify(service).eliminarUsuario(1L);

        // Caso hipotético QA:
        // Si se esperaba HTTP 204 No Content y se obtiene HTTP 200 OK,
        // QA debe reportar inconsistencia en el código de respuesta del endpoint DELETE.
    }
}