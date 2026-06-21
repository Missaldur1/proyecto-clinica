package com.clinic.msusuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        // ARRANGE: preparar usuario válido y simular que el email no existe
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

        // ACT: ejecutar el método real del service
        UsuarioResponseDTO response = service.crearUsuario(request);

        // ASSERT: verificar que la respuesta tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Administrador", response.getNombre());
        assertEquals("admin@test.cl", response.getEmail());
        assertEquals(Rol.ADMIN, response.getRol());
        assertEquals(true, response.getActivo());

        // VERIFY: comprobar que se validó email, se encriptó password y se guardó
        verify(usuarioRepository).existsByEmail("admin@test.cl");
        verify(passwordEncoder).encode("123456");
        verify(usuarioRepository).save(any(Usuario.class));

        // Caso hipotético QA:
        // Si se esperaba crear un usuario y se obtiene error de email duplicado,
        // QA debe reportar que la validación de email puede estar bloqueando
        // registros válidos.
    }

    @Test
    void crearUsuario_debeLanzarExcepcionCuandoEmailYaExiste() {

        // ARRANGE: simular que el email ya está registrado
        when(usuarioRepository.existsByEmail("admin@test.cl"))
                .thenReturn(true);

        // ACT + ASSERT: ejecutar el método y verificar excepción
        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.crearUsuario(request));

        assertEquals(
                "El email ya está registrado: admin@test.cl",
                exception.getMessage());

        // VERIFY: comprobar que no se encriptó ni se guardó el usuario
        verify(usuarioRepository).existsByEmail("admin@test.cl");
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any(Usuario.class));

        // Caso hipotético QA:
        // Si el sistema permite registrar dos usuarios con el mismo email,
        // QA debe reportar una falla en la regla de negocio de duplicidad.
    }

    @Test
    void listarUsuarios_debeRetornarUsuarios() {

        // ARRANGE: preparar una lista simulada de usuarios
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuario));

        // ACT: ejecutar listado de usuarios
        List<UsuarioResponseDTO> response = service.listarUsuarios();

        // ASSERT: verificar que la lista tenga el usuario esperado
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("Administrador", response.get(0).getNombre());
        assertEquals("admin@test.cl", response.get(0).getEmail());
        assertEquals(Rol.ADMIN, response.get(0).getRol());
        assertEquals(true, response.get(0).getActivo());

        // VERIFY: comprobar que se consultó el repositorio
        verify(usuarioRepository).findAll();

        // Caso hipotético QA:
        // Si existen usuarios registrados y el listado retorna vacío,
        // QA debe reportar una falla en el método de listado.
    }

    @Test
    void buscarPorId_debeRetornarUsuarioCuandoExiste() {

        // ARRANGE: preparar un usuario existente
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        // ACT: ejecutar búsqueda por ID
        UsuarioResponseDTO response = service.buscarPorId(1L);

        // ASSERT: verificar que la respuesta corresponda al usuario esperado
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Administrador", response.getNombre());
        assertEquals("admin@test.cl", response.getEmail());
        assertEquals(Rol.ADMIN, response.getRol());
        assertEquals(true, response.getActivo());

        // VERIFY: comprobar que se consultó por ID
        verify(usuarioRepository).findById(1L);

        // Caso hipotético QA:
        // Si se busca un usuario existente y el sistema responde no encontrado,
        // QA debe reportar una falla en la búsqueda por ID.
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoUsuarioNoExiste() {

        // ARRANGE: simular que no existe usuario con ID 99
        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar búsqueda y verificar excepción
        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals(
                "Usuario no encontrado con ID: 99",
                exception.getMessage());

        // VERIFY: comprobar que se consultó por ID
        verify(usuarioRepository).findById(99L);

        // Caso hipotético QA:
        // Si se busca un usuario inexistente y el sistema responde 200 OK,
        // QA debe reportar que no se está manejando correctamente el caso no encontrado.
    }

    @Test
    void actualizarUsuario_debeActualizarUsuarioCuandoExiste() {

        // ARRANGE: preparar usuario existente y datos nuevos
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

        // ACT: ejecutar actualización
        UsuarioResponseDTO response = service.actualizarUsuario(1L, request);

        // ASSERT: verificar que los datos fueron actualizados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Administrador", response.getNombre());
        assertEquals("admin@test.cl", response.getEmail());
        assertEquals(Rol.ADMIN, response.getRol());
        assertEquals(true, response.getActivo());

        // VERIFY: comprobar búsqueda, encriptación y guardado
        verify(usuarioRepository).findById(1L);
        verify(passwordEncoder).encode("123456");
        verify(usuarioRepository).save(any(Usuario.class));

        // Caso hipotético QA:
        // Si se actualiza un usuario y la contraseña queda sin encriptar,
        // QA debe reportar un riesgo de seguridad en la actualización.
    }

    @Test
    void eliminarUsuario_debeEliminarUsuarioCuandoExiste() {

        // ARRANGE: preparar usuario existente
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador");
        usuario.setEmail("admin@test.cl");
        usuario.setPassword("password-encriptada");
        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        // ACT: ejecutar eliminación
        service.eliminarUsuario(1L);

        // VERIFY: comprobar que se buscó y eliminó el usuario
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).delete(usuario);

        // Caso hipotético QA:
        // Si se elimina un usuario existente y sigue apareciendo en el listado,
        // QA debe reportar que la eliminación no se está aplicando correctamente.
    }
}