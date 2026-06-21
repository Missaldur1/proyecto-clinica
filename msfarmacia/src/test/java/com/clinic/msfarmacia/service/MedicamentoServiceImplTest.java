package com.clinic.msfarmacia.service;

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

import com.clinic.msfarmacia.dto.MedicamentoRequestDTO;
import com.clinic.msfarmacia.dto.MedicamentoResponseDTO;
import com.clinic.msfarmacia.exception.MedicamentoDuplicadoException;
import com.clinic.msfarmacia.exception.MedicamentoNotFoundException;
import com.clinic.msfarmacia.model.Medicamento;
import com.clinic.msfarmacia.repository.MedicamentoRepository;

@ExtendWith(MockitoExtension.class)
class MedicamentoServiceImplTest {

    @Mock
    private MedicamentoRepository repository;

    @InjectMocks
    private MedicamentoServiceImpl service;

    private MedicamentoRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new MedicamentoRequestDTO();
        request.setNombre("Paracetamol");
        request.setDescripcion("Analgésico y antipirético");
        request.setLaboratorio("Laboratorio Chile");
        request.setPrecio(2500.0);
        request.setStock(100);
        request.setDisponible(true);
    }

    @Test
    void guardar_debeCrearMedicamentoCuandoNoExisteDuplicado() {

        // ARRANGE: preparar datos válidos y simular que no existe duplicado
        when(repository.findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile"))
                .thenReturn(Optional.empty());

        when(repository.save(any(Medicamento.class)))
                .thenAnswer(invocation -> {
                    Medicamento medicamento = invocation.getArgument(0);
                    medicamento.setId(1L);
                    return medicamento;
                });

        // ACT: ejecutar el método real del service
        MedicamentoResponseDTO response = service.guardar(request);

        // ASSERT: verificar que el medicamento creado tenga los datos esperados
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paracetamol", response.getNombre());
        assertEquals("Analgésico y antipirético", response.getDescripcion());
        assertEquals("Laboratorio Chile", response.getLaboratorio());
        assertEquals(2500.0, response.getPrecio());
        assertEquals(100, response.getStock());
        assertEquals(true, response.getDisponible());

        // VERIFY: comprobar validación de duplicidad y guardado
        verify(repository).findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile");
        verify(repository).save(any(Medicamento.class));

        // Caso hipotético QA:
        // Si se esperaba crear un medicamento válido y se obtiene error de duplicidad,
        // QA debe reportar que la validación por nombre y laboratorio puede estar
        // bloqueando registros válidos.
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMedicamentoYaExiste() {

        // ARRANGE: preparar un medicamento existente con el mismo nombre y laboratorio
        Medicamento medicamentoExistente = new Medicamento();
        medicamentoExistente.setId(1L);
        medicamentoExistente.setNombre("Paracetamol");
        medicamentoExistente.setLaboratorio("Laboratorio Chile");

        when(repository.findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile"))
                .thenReturn(Optional.of(medicamentoExistente));

        // ACT + ASSERT: ejecutar el método y verificar excepción por duplicidad
        MedicamentoDuplicadoException exception = assertThrows(
                MedicamentoDuplicadoException.class,
                () -> service.guardar(request));

        assertEquals(
                "Ya existe un medicamento registrado con el nombre Paracetamol del laboratorio Laboratorio Chile",
                exception.getMessage());

        // VERIFY: comprobar que se consultó duplicidad, pero no se guardó
        verify(repository).findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile");
        verify(repository, never()).save(any(Medicamento.class));

        // Caso hipotético QA:
        // Si el sistema permite registrar dos medicamentos con el mismo nombre
        // y laboratorio, QA debe reportar una falla en la regla de duplicidad.
    }

    @Test
    void buscarPorId_debeRetornarMedicamentoCuandoExiste() {

        // ARRANGE: preparar un medicamento existente en el repositorio simulado
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNombre("Paracetamol");
        medicamento.setDescripcion("Analgésico y antipirético");
        medicamento.setLaboratorio("Laboratorio Chile");
        medicamento.setPrecio(2500.0);
        medicamento.setStock(100);
        medicamento.setDisponible(true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(medicamento));

        // ACT: ejecutar búsqueda por ID
        MedicamentoResponseDTO response = service.buscarPorId(1L);

        // ASSERT: verificar que la respuesta corresponda al medicamento esperado
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paracetamol", response.getNombre());
        assertEquals("Laboratorio Chile", response.getLaboratorio());
        assertEquals(2500.0, response.getPrecio());
        assertEquals(100, response.getStock());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(1L);

        // Caso hipotético QA:
        // Si se busca un medicamento existente y el sistema responde no encontrado,
        // QA debe reportar una falla en la búsqueda por ID.
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoMedicamentoNoExiste() {

        // ARRANGE: simular que no existe medicamento con ID 99
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT: ejecutar búsqueda y verificar excepción
        MedicamentoNotFoundException exception = assertThrows(
                MedicamentoNotFoundException.class,
                () -> service.buscarPorId(99L));

        assertEquals(
                "Medicamento no encontrado",
                exception.getMessage());

        // VERIFY: comprobar que se consultó el repositorio por ID
        verify(repository).findById(99L);

        // Caso hipotético QA:
        // Si se busca un medicamento inexistente y el sistema responde 200 OK,
        // QA debe reportar que no se está manejando correctamente el caso no encontrado.
    }
}