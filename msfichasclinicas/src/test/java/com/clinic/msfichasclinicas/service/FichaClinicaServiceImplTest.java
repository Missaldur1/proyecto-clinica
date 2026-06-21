package com.clinic.msfichasclinicas.service;

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

import com.clinic.msfichasclinicas.client.ExamenClient;
import com.clinic.msfichasclinicas.client.MedicoClient;
import com.clinic.msfichasclinicas.client.PacienteClient;
import com.clinic.msfichasclinicas.dto.FichaClinicaRequestDTO;
import com.clinic.msfichasclinicas.dto.FichaClinicaResponseDTO;
import com.clinic.msfichasclinicas.exception.ExamenNotFoundException;
import com.clinic.msfichasclinicas.exception.FichaNotFoundException;
import com.clinic.msfichasclinicas.exception.MedicoNotFoundException;
import com.clinic.msfichasclinicas.exception.PacienteNotFoundException;
import com.clinic.msfichasclinicas.model.FichaClinica;
import com.clinic.msfichasclinicas.repository.FichaRepository;

@ExtendWith(MockitoExtension.class)
class FichaClinicaServiceImplTest {

    @Mock
    private FichaRepository repository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private MedicoClient medicoClient;

    @Mock
    private ExamenClient examenClient;

    @InjectMocks
    private FichaClinicaServiceImpl service;

    private FichaClinicaRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new FichaClinicaRequestDTO();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setExamenId(3L);
        request.setDiagnostico("Paciente con evolución favorable");
        request.setTratamiento("Reposo e hidratación");
        request.setObservaciones("Sin observaciones adicionales");
        request.setFecha(LocalDate.now());
    }

    @Test
    void crear_debeCrearFichaClinicaCuandoDatosSonValidos() {

        // ARRANGE: preparar datos válidos y simular que paciente, médico y examen existen
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        when(examenClient.buscarExamen(3L))
                .thenReturn(new Object());

        when(repository.save(any(FichaClinica.class)))
                .thenAnswer(invocation -> {
                    FichaClinica ficha = invocation.getArgument(0);
                    ficha.setId(1L);
                    return ficha;
                });

        // ACT: ejecutar el método real del service
        FichaClinicaResponseDTO response = service.crear(request);

        // ASSERT: verificar que la ficha clínica creada tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(2L, response.getMedicoId());
        assertEquals(3L, response.getExamenId());
        assertEquals("Paciente con evolución favorable", response.getDiagnostico());
        assertEquals("Reposo e hidratación", response.getTratamiento());
        assertEquals("Sin observaciones adicionales", response.getObservaciones());
        assertEquals(request.getFecha(), response.getFecha());

        // VERIFY: comprobar llamadas a Feign Clients y Repository
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(examenClient).buscarExamen(3L);
        verify(repository).save(any(FichaClinica.class));

        // Caso hipotético QA:
        // Si se esperaba crear una ficha clínica válida y se obtiene error,
        // QA debe reportar que alguna validación remota o el guardado de la ficha
        // está bloqueando registros válidos.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoPacienteNoExiste() {

        // ARRANGE: simular que el PacienteClient no encuentra al paciente
        when(pacienteClient.buscarPaciente(1L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        // ACT + ASSERT: ejecutar creación y verificar excepción
        assertThrows(
                PacienteNotFoundException.class,
                () -> service.crear(request));

        // VERIFY: comprobar que solo se consultó paciente y no se avanzó al resto
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient, never()).buscarMedico(2L);
        verify(examenClient, never()).buscarExamen(3L);
        verify(repository, never()).save(any(FichaClinica.class));

        // Caso hipotético QA:
        // Si el sistema permite crear una ficha para un paciente inexistente,
        // QA debe reportar una falla de interoperabilidad con mspacientes.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoMedicoNoExiste() {

        // ARRANGE: simular paciente existente, pero médico inexistente
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenThrow(new RuntimeException("Médico no encontrado"));

        // ACT + ASSERT: ejecutar creación y verificar excepción
        assertThrows(
                MedicoNotFoundException.class,
                () -> service.crear(request));

        // VERIFY: comprobar que no se consultó examen ni se guardó ficha
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(examenClient, never()).buscarExamen(3L);
        verify(repository, never()).save(any(FichaClinica.class));

        // Caso hipotético QA:
        // Si el sistema permite crear una ficha con médico inexistente,
        // QA debe reportar una falla en la validación remota de médicos.
    }

    @Test
    void crear_debeLanzarExcepcionCuandoExamenNoExiste() {

        // ARRANGE: simular paciente y médico existentes, pero examen inexistente
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        when(examenClient.buscarExamen(3L))
                .thenThrow(new RuntimeException("Examen no encontrado"));

        // ACT + ASSERT: ejecutar creación y verificar excepción
        assertThrows(
                ExamenNotFoundException.class,
                () -> service.crear(request));

        // VERIFY: comprobar que se validaron las relaciones, pero no se guardó
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(examenClient).buscarExamen(3L);
        verify(repository, never()).save(any(FichaClinica.class));

        // Caso hipotético QA:
        // Si el sistema permite crear una ficha asociada a un examen inexistente,
        // QA debe reportar una falla en la validación remota de exámenes.
    }

    @Test
    void buscarPorId_debeRetornarFichaCuandoExiste() {

        // ARRANGE: preparar ficha clínica existente en el repositorio simulado
        FichaClinica ficha = new FichaClinica();
        ficha.setId(1L);
        ficha.setPacienteId(1L);
        ficha.setMedicoId(2L);
        ficha.setExamenId(3L);
        ficha.setDiagnostico("Paciente con evolución favorable");
        ficha.setTratamiento("Reposo e hidratación");
        ficha.setObservaciones("Sin observaciones adicionales");
        ficha.setFecha(LocalDate.now());

        when(repository.findById(1L))
                .thenReturn(Optional.of(ficha));

        // ACT: ejecutar búsqueda por ID
        FichaClinicaResponseDTO response = service.buscarPorId(1L);

        // ASSERT: verificar que la respuesta corresponda a la ficha esperada
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(2L, response.getMedicoId());
        assertEquals(3L, response.getExamenId());
        assertEquals("Paciente con evolución favorable", response.getDiagnostico());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(1L);

        // Caso hipotético QA:
        // Si se busca una ficha existente y el sistema responde no encontrada,
        // QA debe reportar una falla en la búsqueda por ID.
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoFichaNoExiste() {

        // ARRANGE: simular que no existe ficha clínica con ID 99
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar búsqueda y verificar excepción
        assertThrows(
                FichaNotFoundException.class,
                () -> service.buscarPorId(99L));

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(99L);

        // Caso hipotético QA:
        // Si se busca una ficha inexistente y el sistema responde 200 OK,
        // QA debe reportar que no se está manejando correctamente el caso no encontrado.
    }
}