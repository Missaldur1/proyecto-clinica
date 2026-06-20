package com.clinic.msmedicos.service;

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

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;
import com.clinic.msmedicos.exception.MedicoDuplicadoException;
import com.clinic.msmedicos.exception.MedicoNotFoundException;
import com.clinic.msmedicos.model.Medico;
import com.clinic.msmedicos.repository.MedicoRepository;

@ExtendWith(MockitoExtension.class)
class MedicoServiceImplTest {

    @Mock
    private MedicoRepository repository;

    @InjectMocks
    private MedicoServiceImpl service;

    private MedicoRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new MedicoRequestDTO();
        request.setRut("22222222-2");
        request.setNombre("Ana");
        request.setApellido("Pérez");
        request.setEspecialidad("Cardiología");
        request.setTelefono("912345678");
        request.setCorreo("ana.perez@clinica.cl");
        request.setDisponible(true);
    }

    @Test
    void guardar_debeCrearMedicoCuandoRutYCorreoNoExisten() {

        when(repository.findByRut("22222222-2"))
                .thenReturn(Optional.empty());

        when(repository.findByCorreo("ana.perez@clinica.cl"))
                .thenReturn(Optional.empty());

        when(repository.save(any(Medico.class)))
                .thenAnswer(invocation -> {
                    Medico medico = invocation.getArgument(0);
                    medico.setId(1L);
                    return medico;
                });

        MedicoResponseDTO response = service.guardar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("22222222-2", response.getRut());
        assertEquals("Ana", response.getNombre());
        assertEquals("Pérez", response.getApellido());
        assertEquals("Cardiología", response.getEspecialidad());
        assertEquals("912345678", response.getTelefono());
        assertEquals("ana.perez@clinica.cl", response.getCorreo());
        assertEquals(true, response.getDisponible());

        verify(repository).findByRut("22222222-2");
        verify(repository).findByCorreo("ana.perez@clinica.cl");
        verify(repository).save(any(Medico.class));
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoRutYaExiste() {

        Medico medicoExistente = new Medico();
        medicoExistente.setId(1L);
        medicoExistente.setRut("22222222-2");

        when(repository.findByRut("22222222-2"))
                .thenReturn(Optional.of(medicoExistente));

        MedicoDuplicadoException exception = assertThrows(
                MedicoDuplicadoException.class,
                () -> service.guardar(request));

        assertEquals(
                "Ya existe un médico registrado con el RUT 22222222-2",
                exception.getMessage());

        verify(repository).findByRut("22222222-2");
        verify(repository, never()).findByCorreo("ana.perez@clinica.cl");
        verify(repository, never()).save(any(Medico.class));
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoCorreoYaExiste() {

        Medico medicoExistente = new Medico();
        medicoExistente.setId(1L);
        medicoExistente.setCorreo("ana.perez@clinica.cl");

        when(repository.findByRut("22222222-2"))
                .thenReturn(Optional.empty());

        when(repository.findByCorreo("ana.perez@clinica.cl"))
                .thenReturn(Optional.of(medicoExistente));

        MedicoDuplicadoException exception = assertThrows(
                MedicoDuplicadoException.class,
                () -> service.guardar(request));

        assertEquals(
                "Ya existe un médico registrado con el correo ana.perez@clinica.cl",
                exception.getMessage());

        verify(repository).findByRut("22222222-2");
        verify(repository).findByCorreo("ana.perez@clinica.cl");
        verify(repository, never()).save(any(Medico.class));
    }

    @Test
    void buscarPorId_debeRetornarMedicoCuandoExiste() {

        Medico medico = new Medico();
        medico.setId(1L);
        medico.setRut("22222222-2");
        medico.setNombre("Ana");
        medico.setApellido("Pérez");
        medico.setEspecialidad("Cardiología");
        medico.setTelefono("912345678");
        medico.setCorreo("ana.perez@clinica.cl");
        medico.setDisponible(true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(medico));

        MedicoResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("22222222-2", response.getRut());
        assertEquals("Ana", response.getNombre());
        assertEquals("Cardiología", response.getEspecialidad());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoMedicoNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                MedicoNotFoundException.class,
                () -> service.buscarPorId(99L));

        verify(repository).findById(99L);
    }
}