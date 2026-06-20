package com.clinic.mspacientes.service;

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

import com.clinic.mspacientes.dto.PacienteRequestDTO;
import com.clinic.mspacientes.dto.PacienteResponseDTO;
import com.clinic.mspacientes.exception.PacienteDuplicadoException;
import com.clinic.mspacientes.exception.PacienteNotFoundException;
import com.clinic.mspacientes.model.Paciente;
import com.clinic.mspacientes.repository.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteServiceImpl service;

    private PacienteRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new PacienteRequestDTO();
        request.setRut("11111111-1");
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setEdad(30);
        request.setPrevision("FONASA");
        request.setTelefono("912345678");
        request.setEmail("juan.perez@correo.cl");
    }

    @Test
    void crearPaciente_debeCrearPacienteCuandoRutNoExiste() {

        when(repository.findByRut("11111111-1"))
                .thenReturn(Optional.empty());

        when(repository.save(any(Paciente.class)))
                .thenAnswer(invocation -> {
                    Paciente paciente = invocation.getArgument(0);
                    paciente.setId(1L);
                    return paciente;
                });

        PacienteResponseDTO response = service.crearPaciente(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("11111111-1", response.getRut());
        assertEquals("Juan", response.getNombre());
        assertEquals("Pérez", response.getApellido());
        assertEquals(30, response.getEdad());
        assertEquals("FONASA", response.getPrevision());
        assertEquals("912345678", response.getTelefono());
        assertEquals("juan.perez@correo.cl", response.getEmail());

        verify(repository).findByRut("11111111-1");
        verify(repository).save(any(Paciente.class));
    }

    @Test
    void crearPaciente_debeLanzarExcepcionCuandoRutYaExiste() {

        Paciente pacienteExistente = new Paciente();
        pacienteExistente.setId(1L);
        pacienteExistente.setRut("11111111-1");

        when(repository.findByRut("11111111-1"))
                .thenReturn(Optional.of(pacienteExistente));

        PacienteDuplicadoException exception = assertThrows(
                PacienteDuplicadoException.class,
                () -> service.crearPaciente(request));

        assertEquals(
                "Ya existe un paciente registrado con el RUT 11111111-1",
                exception.getMessage());

        verify(repository).findByRut("11111111-1");
        verify(repository, never()).save(any(Paciente.class));
    }

    @Test
    void buscarPorId_debeRetornarPacienteCuandoExiste() {

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setRut("11111111-1");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setEdad(30);
        paciente.setPrevision("FONASA");
        paciente.setTelefono("912345678");
        paciente.setEmail("juan.perez@correo.cl");

        when(repository.findById(1L))
                .thenReturn(Optional.of(paciente));

        PacienteResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("11111111-1", response.getRut());
        assertEquals("Juan", response.getNombre());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoPacienteNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals(
                "Paciente no encontrado",
                exception.getMessage());

        verify(repository).findById(99L);
    }
}