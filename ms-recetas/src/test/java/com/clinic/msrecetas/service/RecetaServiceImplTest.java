package com.clinic.msrecetas.service;

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

import com.clinic.msrecetas.client.MedicoClient;
import com.clinic.msrecetas.client.PacienteClient;
import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.exception.RecetaInactivaException;
import com.clinic.msrecetas.exception.ReglaNegocioException;
import com.clinic.msrecetas.model.Receta;
import com.clinic.msrecetas.repository.RecetaRepository;

@ExtendWith(MockitoExtension.class)
class RecetaServiceImplTest {

    @Mock
    private RecetaRepository repository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private MedicoClient medicoClient;

    @InjectMocks
    private RecetaServiceImpl service;

    private RecetaRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new RecetaRequestDTO();
        request.setPacienteId(1L);
        request.setMedicoId(2L);
        request.setMedicamento("Paracetamol");
        request.setDosis("500mg cada 8 horas");
        request.setIndicaciones("Tomar después de las comidas");
        request.setActiva(true);
    }

    @Test
    void guardar_debeCrearRecetaCuandoDatosSonValidos() {

        // ARRANGE: preparar datos válidos y simular que paciente y médico existen
        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        when(repository.save(any(Receta.class)))
                .thenAnswer(invocation -> {
                    Receta receta = invocation.getArgument(0);
                    receta.setId(1L);
                    return receta;
                });

        // ACT: ejecutar el método real del service
        RecetaResponseDTO response = service.guardar(request);

        // ASSERT: verificar que la receta creada tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getPacienteId());
        assertEquals(2L, response.getMedicoId());
        assertEquals("Paracetamol", response.getMedicamento());
        assertEquals("500mg cada 8 horas", response.getDosis());
        assertEquals("Tomar después de las comidas", response.getIndicaciones());
        assertEquals(LocalDate.now(), response.getFechaEmision());
        assertEquals(true, response.getActiva());

        // VERIFY: comprobar llamadas a Feign Clients y Repository
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(repository).save(any(Receta.class));

        // Caso hipotético QA:
        // Si se esperaba crear una receta válida y se obtiene error de paciente o médico,
        // QA debe reportar que la validación remota puede estar bloqueando recetas válidas.
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMedicamentoEstaVacio() {

        // ARRANGE: preparar una receta con medicamento vacío
        request.setMedicamento("");

        when(pacienteClient.buscarPaciente(1L))
                .thenReturn(new Object());

        when(medicoClient.buscarMedico(2L))
                .thenReturn(new Object());

        // ACT + ASSERT: ejecutar guardado y verificar excepción por regla de negocio
        ReglaNegocioException exception = assertThrows(
                ReglaNegocioException.class,
                () -> service.guardar(request));

        assertEquals(
                "La receta debe contener un medicamento",
                exception.getMessage());

        // VERIFY: comprobar que se validó paciente y médico, pero no se guardó
        verify(pacienteClient).buscarPaciente(1L);
        verify(medicoClient).buscarMedico(2L);
        verify(repository, never()).save(any(Receta.class));

        // Caso hipotético QA:
        // Si el sistema permite guardar una receta sin medicamento,
        // QA debe reportar una falla en la regla de negocio de medicamento obligatorio.
    }

    @Test
    void actualizar_debeLanzarExcepcionCuandoRecetaEstaInactiva() {

        // ARRANGE: preparar una receta existente marcada como inactiva
        Receta recetaInactiva = new Receta();
        recetaInactiva.setId(1L);
        recetaInactiva.setPacienteId(1L);
        recetaInactiva.setMedicoId(2L);
        recetaInactiva.setMedicamento("Paracetamol");
        recetaInactiva.setDosis("500mg cada 8 horas");
        recetaInactiva.setIndicaciones("Tomar después de las comidas");
        recetaInactiva.setFechaEmision(LocalDate.now());
        recetaInactiva.setActiva(false);

        when(repository.findById(1L))
                .thenReturn(Optional.of(recetaInactiva));

        // ACT + ASSERT: ejecutar actualización y verificar excepción por receta inactiva
        RecetaInactivaException exception = assertThrows(
                RecetaInactivaException.class,
                () -> service.actualizar(1L, request));

        assertNotNull(exception);

        // VERIFY: comprobar que se buscó la receta, pero no se guardó la actualización
        verify(repository).findById(1L);
        verify(repository, never()).save(any(Receta.class));

        // Caso hipotético QA:
        // Si el sistema permite modificar una receta inactiva,
        // QA debe reportar que no se está respetando la regla de bloqueo
        // para recetas cerradas o desactivadas.
    }
}