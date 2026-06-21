package com.clinic.msusuarios.controller;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clinic.msusuarios.dto.LoginRequestDTO;
import com.clinic.msusuarios.enums.Rol;
import com.clinic.msusuarios.exception.GlobalExceptionHandler;
import com.clinic.msusuarios.model.Usuario;
import com.clinic.msusuarios.repository.UsuarioRepository;
import com.clinic.msusuarios.security.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private LoginRequestDTO request;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        AuthController controller = new AuthController(
                usuarioRepository,
                passwordEncoder,
                jwtService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();

        request = new LoginRequestDTO();
        request.setEmail("admin@test.cl");
        request.setPassword("123456");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);
    }

    @Test
    void login_debeRetornarStatus200YTokenCuandoCredencialesSonValidas() throws Exception {

        // ARRANGE: preparar usuario existente, contraseña válida y token simulado
        when(usuarioRepository.findByEmail("admin@test.cl"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches("123456", "password-encriptada"))
                .thenReturn(true);

        when(jwtService.generateToken("admin@test.cl", "ADMIN"))
                .thenReturn("token-jwt-simulado");

        // ACT + ASSERT: ejecutar endpoint POST /auth/login y validar respuesta
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-jwt-simulado"));

        // VERIFY: comprobar que se buscó usuario, se validó password y se generó token
        verify(usuarioRepository).findByEmail("admin@test.cl");
        verify(passwordEncoder).matches("123456", "password-encriptada");
        verify(jwtService).generateToken("admin@test.cl", "ADMIN");

        // Caso hipotético QA:
        // Si se ingresan credenciales válidas y no se devuelve token,
        // QA debe reportar falla en el login o en la generación del JWT.
    }

    @Test
    void login_debeRetornarStatus401CuandoEmailNoExiste() throws Exception {

        // ARRANGE: simular que el correo no existe en la base de datos
        when(usuarioRepository.findByEmail("admin@test.cl"))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar login y validar respuesta Unauthorized
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Correo o contraseña incorrectos"))
                .andExpect(jsonPath("$.path").value("/auth/login"));

        // VERIFY: comprobar que se buscó por email
        verify(usuarioRepository).findByEmail("admin@test.cl");

        // Caso hipotético QA:
        // Si se intenta iniciar sesión con un correo inexistente y el sistema responde 200 OK,
        // QA debe reportar una falla crítica de autenticación.
    }

    @Test
    void login_debeRetornarStatus401CuandoPasswordEsIncorrecta() throws Exception {

        // ARRANGE: simular usuario existente, pero contraseña incorrecta
        when(usuarioRepository.findByEmail("admin@test.cl"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches("123456", "password-encriptada"))
                .thenReturn(false);

        // ACT + ASSERT: ejecutar login y validar respuesta Unauthorized
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Correo o contraseña incorrectos"))
                .andExpect(jsonPath("$.path").value("/auth/login"));

        // VERIFY: comprobar que se buscó usuario y se validó password
        verify(usuarioRepository).findByEmail("admin@test.cl");
        verify(passwordEncoder).matches("123456", "password-encriptada");

        // Caso hipotético QA:
        // Si se ingresa una contraseña incorrecta y el sistema entrega token,
        // QA debe reportar una falla crítica en la validación de credenciales.
    }
}