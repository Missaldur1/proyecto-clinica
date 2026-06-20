package com.clinic.msexamenes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clinic.msexamenes.client.PacienteClient;
import com.clinic.msexamenes.dto.ExamenRequestDTO;
import com.clinic.msexamenes.dto.ExamenResponseDTO;
import com.clinic.msexamenes.exception.ExamenNotFoundException;
import com.clinic.msexamenes.exception.PacienteNotFoundException;
import com.clinic.msexamenes.model.Examen;
import com.clinic.msexamenes.repository.ExamenRepository;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @Mock
    private ExamenRepository repository;

    @Mock
    private PacienteClient pacienteClient;

    @InjectMocks
    private ExamenServiceImpl service;

    private ExamenRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new ExamenRequestDTO();
        request.setPacienteId(1L);
        request.setTipoExamen("Hemograma");
        request.setFecha(LocalDate.now());
        request.setResultado("Resultados dentro de rangos normales");
        request.setEstado("COMPLETADO");
    }

    @Test
    void crear_debeCrearExamenCuandoDatosSonValidos() {

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(repository.save(any(Examen.class)))
                .thenAnswer(invocation -> {
                    Examen examen = invocation.getArgument(0);
                    examen.setId(1L);
                    return examen;
                });

        ExamenResponseDTO response = service.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Hemograma", response.getTipoExamen());
        assertEquals(request.getFecha(), response.getFecha());
        assertEquals("Resultados dentro de rangos normales", response.getResultado());
        assertEquals("COMPLETADO", response.getEstado());

        verify(pacienteClient).buscarPaciente(1L);
        verify(repository).save(any(Examen.class));
    }

    @Test
    void crear_debeLanzarExcepcionCuandoPacienteNoExiste() {

        when(pacienteClient.buscarPaciente(1L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> service.crear(request));

        assertEquals("Paciente no encontrado", exception.getMessage());

        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Examen.class));
    }

    @Test
    void buscarPorId_debeRetornarExamenCuandoExiste() {

        Examen examen = new Examen();
        examen.setId(1L);
        examen.setPacienteId(1L);
        examen.setTipoExamen("Hemograma");
        examen.setFecha(LocalDate.now());
        examen.setResultado("Resultados dentro de rangos normales");
        examen.setEstado("COMPLETADO");

        when(repository.findById(1L))
                .thenReturn(Optional.of(examen));

        ExamenResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Hemograma", response.getTipoExamen());
        assertEquals("COMPLETADO", response.getEstado());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoExamenNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        ExamenNotFoundException exception = assertThrows(
                ExamenNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Examen no encontrado", exception.getMessage());

        verify(repository).findById(99L);
    }

    @Test
    void actualizar_debeActualizarExamenCuandoDatosSonValidos() {

        Examen examenExistente = new Examen();
        examenExistente.setId(1L);
        examenExistente.setPacienteId(1L);
        examenExistente.setTipoExamen("Hemograma");
        examenExistente.setFecha(LocalDate.now());
        examenExistente.setResultado("Pendiente");
        examenExistente.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(examenExistente));

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(repository.save(any(Examen.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExamenResponseDTO response = service.actualizar(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Hemograma", response.getTipoExamen());
        assertEquals("Resultados dentro de rangos normales", response.getResultado());
        assertEquals("COMPLETADO", response.getEstado());

        verify(repository).findById(1L);
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository).save(any(Examen.class));
    }

    @Test
    void actualizar_debeLanzarExcepcionCuandoPacienteNoExiste() {

        Examen examenExistente = new Examen();
        examenExistente.setId(1L);
        examenExistente.setPacienteId(1L);
        examenExistente.setTipoExamen("Hemograma");
        examenExistente.setFecha(LocalDate.now());
        examenExistente.setResultado("Pendiente");
        examenExistente.setEstado("PENDIENTE");

        when(repository.findById(1L))
                .thenReturn(Optional.of(examenExistente));

        when(pacienteClient.buscarPaciente(1L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> service.actualizar(1L, request));

        assertEquals("Paciente no encontrado", exception.getMessage());

        verify(repository).findById(1L);
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Examen.class));
    }
}