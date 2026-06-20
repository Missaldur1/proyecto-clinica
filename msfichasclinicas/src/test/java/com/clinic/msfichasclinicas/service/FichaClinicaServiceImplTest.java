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

        FichaClinicaResponseDTO response = service.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(2L, response.getMedicoId());
        assertEquals(3L, response.getExamenId());
        assertEquals("Paciente con evolución favorable", response.getDiagnostico());
        assertEquals("Reposo e hidratación", response.getTratamiento());
        assertEquals("Sin observaciones adicionales", response.getObservaciones());
        assertEquals(request.getFecha(), response.getFecha());

        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(examenClient).buscarExamen(3L);
        verify(repository).save(any(FichaClinica.class));
    }

    @Test
    void crear_debeLanzarExcepcionCuandoPacienteNoExiste() {

        when(pacienteClient.buscarPaciente(1L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        assertThrows(
                PacienteNotFoundException.class,
                () -> service.crear(request));

        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient, never()).buscarMedico(2L);
        verify(examenClient, never()).buscarExamen(3L);
        verify(repository, never()).save(any(FichaClinica.class));
    }

    @Test
    void crear_debeLanzarExcepcionCuandoMedicoNoExiste() {

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenThrow(new RuntimeException("Médico no encontrado"));

        assertThrows(
                MedicoNotFoundException.class,
                () -> service.crear(request));

        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(examenClient, never()).buscarExamen(3L);
        verify(repository, never()).save(any(FichaClinica.class));
    }

    @Test
    void crear_debeLanzarExcepcionCuandoExamenNoExiste() {

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        when(examenClient.buscarExamen(3L))
                .thenThrow(new RuntimeException("Examen no encontrado"));

        assertThrows(
                ExamenNotFoundException.class,
                () -> service.crear(request));

        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(examenClient).buscarExamen(3L);
        verify(repository, never()).save(any(FichaClinica.class));
    }

    @Test
    void buscarPorId_debeRetornarFichaCuandoExiste() {

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

        FichaClinicaResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(2L, response.getMedicoId());
        assertEquals(3L, response.getExamenId());
        assertEquals("Paciente con evolución favorable", response.getDiagnostico());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoFichaNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                FichaNotFoundException.class,
                () -> service.buscarPorId(99L));

        verify(repository).findById(99L);
    }
}