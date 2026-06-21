package com.clinic.msexamenes.service;

import java.time.LocalDate;
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

        // ARRANGE: preparar datos válidos y simular que el paciente existe
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(repository.save(any(Examen.class)))
                .thenAnswer(invocation -> {
                    Examen examen = invocation.getArgument(0);
                    examen.setId(1L);
                    return examen;
                });

        // ACT: ejecutar el método real del service
        ExamenResponseDTO response = service.crear(request);

        // ASSERT: verificar que el examen creado tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Hemograma", response.getTipoExamen());
        assertEquals(request.getFecha(), response.getFecha());
        assertEquals("Resultados dentro de rangos normales", response.getResultado());
        assertEquals("COMPLETADO", response.getEstado());

        // VERIFY: comprobar que se consultó el paciente y se guardó el examen
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository).save(any(Examen.class));

        // Caso hipotético QA:
        // Si se esperaba crear un examen válido y se obtiene error de paciente,
        // QA debe reportar que la validación remota del paciente está bloqueando
        // registros válidos.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoPacienteNoExiste() {

        // ARRANGE: simular que el PacienteClient no encuentra al paciente
        when(pacienteClient.buscarPaciente(1L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        // ACT + ASSERT: ejecutar creación y verificar excepción
        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> service.crear(request));

        assertEquals(
                "Paciente no encontrado",
                exception.getMessage());

        // VERIFY: comprobar que se consultó el paciente, pero no se guardó el examen
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Examen.class));

        // Caso hipotético QA:
        // Si el sistema permite crear exámenes para pacientes inexistentes,
        // QA debe reportar una falla en la validación de interoperabilidad
        // entre msexamenes y mspacientes.
    }

    @Test
    void buscarPorId_debeRetornarExamenCuandoExiste() {

        // ARRANGE: preparar un examen existente en el repositorio simulado
        Examen examen = new Examen();
        examen.setId(1L);
        examen.setPacienteId(1L);
        examen.setTipoExamen("Hemograma");
        examen.setFecha(LocalDate.now());
        examen.setResultado("Resultados dentro de rangos normales");
        examen.setEstado("COMPLETADO");

        when(repository.findById(1L))
                .thenReturn(Optional.of(examen));

        // ACT: ejecutar búsqueda por ID
        ExamenResponseDTO response = service.buscarPorId(1L);

        // ASSERT: verificar que la respuesta corresponda al examen esperado
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Hemograma", response.getTipoExamen());
        assertEquals("COMPLETADO", response.getEstado());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(1L);

        // Caso hipotético QA:
        // Si se busca un examen existente y el sistema responde no encontrado,
        // QA debe reportar una falla en la búsqueda por ID.
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoExamenNoExiste() {

        // ARRANGE: simular que no existe examen con ID 99
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar búsqueda y verificar excepción
        ExamenNotFoundException exception = assertThrows(
                ExamenNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals(
                "Examen no encontrado",
                exception.getMessage());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(99L);

        // Caso hipotético QA:
        // Si se busca un examen inexistente y el sistema responde 200 OK,
        // QA debe reportar que no se está manejando correctamente el caso no encontrado.
    }

    @Test
    void actualizar_debeActualizarExamenCuandoDatosSonValidos() {

        // ARRANGE: preparar examen existente y simular que el paciente existe
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

        // ACT: ejecutar actualización
        ExamenResponseDTO response = service.actualizar(1L, request);

        // ASSERT: verificar que los datos fueron actualizados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals("Hemograma", response.getTipoExamen());
        assertEquals("Resultados dentro de rangos normales", response.getResultado());
        assertEquals("COMPLETADO", response.getEstado());

        // VERIFY: comprobar búsqueda, validación remota y guardado
        verify(repository).findById(1L);
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository).save(any(Examen.class));

        // Caso hipotético QA:
        // Si se actualiza un examen válido y los datos no cambian,
        // QA debe reportar una falla en el método de actualización del service.
    }

    @Test
    void actualizar_debeLanzarExcepcionCuandoPacienteNoExiste() {

        // ARRANGE: preparar examen existente, pero simular paciente inexistente
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

        // ACT + ASSERT: ejecutar actualización y verificar excepción
        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> service.actualizar(1L, request));

        assertEquals(
                "Paciente no encontrado",
                exception.getMessage());

        // VERIFY: comprobar que se buscó el examen y se validó el paciente,
        // pero no se guardó la actualización
        verify(repository).findById(1L);
        verify(pacienteClient).buscarPaciente(1L);
        verify(repository, never()).save(any(Examen.class));

        // Caso hipotético QA:
        // Si el sistema permite actualizar un examen asignándolo a un paciente inexistente,
        // QA debe reportar una falla en la validación de relación con pacientes.
    }
}