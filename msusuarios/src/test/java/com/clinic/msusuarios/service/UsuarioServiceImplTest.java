package com.clinic.msusuarios.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.clinic.msusuarios.dto.UsuarioRequestDTO;
import com.clinic.msusuarios.dto.UsuarioResponseDTO;
import com.clinic.msusuarios.enums.Rol;
import com.clinic.msusuarios.exception.EmailAlreadyExistsException;
import com.clinic.msusuarios.exception.UsuarioNotFoundException;
import com.clinic.msusuarios.model.Usuario;
import com.clinic.msusuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl service;

    private UsuarioRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new UsuarioRequestDTO();
        request.setNombre("Administrador");
        request.setEmail("admin@test.cl");
        request.setPassword("123456");
        request.setRol(Rol.ADMIN);
        request.setActivo(true);
    }

    @Test
    void crearUsuario_debeCrearUsuarioCuandoEmailNoExiste() {

        when(usuarioRepository.existsByEmail("admin@test.cl"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("password-encriptada");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> {
                    Usuario usuario = invocation.getArgument(0);
                    usuario.setId(1L);
                    return usuario;
                });

        UsuarioResponseDTO response = service.crearUsuario(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Administrador", response.getNombre());
        assertEquals("admin@test.cl", response.getEmail());
        assertEquals(Rol.ADMIN, response.getRol());
        assertEquals(true, response.getActivo());

        verify(usuarioRepository).existsByEmail("admin@test.cl");
        verify(passwordEncoder).encode("123456");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearUsuario_debeLanzarExcepcionCuandoEmailYaExiste() {

        when(usuarioRepository.existsByEmail("admin@test.cl"))
                .thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.crearUsuario(request));

        assertEquals(
                "El email ya está registrado: admin@test.cl",
                exception.getMessage());

        verify(usuarioRepository).existsByEmail("admin@test.cl");
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void listarUsuarios_debeRetornarUsuarios() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuario));

        List<UsuarioResponseDTO> response = service.listarUsuarios();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("Administrador", response.get(0).getNombre());
        assertEquals("admin@test.cl", response.get(0).getEmail());
        assertEquals(Rol.ADMIN, response.get(0).getRol());
        assertEquals(true, response.get(0).getActivo());

        verify(usuarioRepository).findAll();
    }

    @Test
    void buscarPorId_debeRetornarUsuarioCuandoExiste() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Administrador", response.getNombre());
        assertEquals("admin@test.cl", response.getEmail());
        assertEquals(Rol.ADMIN, response.getRol());
        assertEquals(true, response.getActivo());

        verify(usuarioRepository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoUsuarioNoExiste() {

        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals(
                "Usuario no encontrado con ID: 99",
                exception.getMessage());

        verify(usuarioRepository).findById(99L);
    }

    @Test
    void actualizarUsuario_debeActualizarUsuarioCuandoExiste() {

        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setNombre("Nombre anterior");
        existente.setEmail("anterior@test.cl");
        existente.setPassword("password-antigua");
        existente.setRol(Rol.PACIENTE);
        existente.setActivo(false);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(passwordEncoder.encode("123456"))
                .thenReturn("password-nueva-encriptada");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDTO response = service.actualizarUsuario(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Administrador", response.getNombre());
        assertEquals("admin@test.cl", response.getEmail());
        assertEquals(Rol.ADMIN, response.getRol());
        assertEquals(true, response.getActivo());

        verify(usuarioRepository).findById(1L);
        verify(passwordEncoder).encode("123456");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuario_debeEliminarUsuarioCuandoExiste() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        service.eliminarUsuario(1L);

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).delete(usuario);
    }
}