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

        MedicamentoResponseDTO response = service.guardar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paracetamol", response.getNombre());
        assertEquals("Analgésico y antipirético", response.getDescripcion());
        assertEquals("Laboratorio Chile", response.getLaboratorio());
        assertEquals(2500.0, response.getPrecio());
        assertEquals(100, response.getStock());
        assertEquals(true, response.getDisponible());

        verify(repository).findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile");

        verify(repository).save(any(Medicamento.class));
    }

    @Test
    void guardar_debeLanzarExcepcionCuandoMedicamentoYaExiste() {

        Medicamento medicamentoExistente = new Medicamento();
        medicamentoExistente.setId(1L);
        medicamentoExistente.setNombre("Paracetamol");
        medicamentoExistente.setLaboratorio("Laboratorio Chile");

        when(repository.findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile"))
                .thenReturn(Optional.of(medicamentoExistente));

        MedicamentoDuplicadoException exception = assertThrows(
                MedicamentoDuplicadoException.class,
                () -> service.guardar(request));

        assertEquals(
                "Ya existe un medicamento registrado con el nombre Paracetamol del laboratorio Laboratorio Chile",
                exception.getMessage());

        verify(repository).findByNombreIgnoreCaseAndLaboratorioIgnoreCase(
                "Paracetamol",
                "Laboratorio Chile");

        verify(repository, never()).save(any(Medicamento.class));
    }

    @Test
    void buscarPorId_debeRetornarMedicamentoCuandoExiste() {

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

        MedicamentoResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paracetamol", response.getNombre());
        assertEquals("Laboratorio Chile", response.getLaboratorio());
        assertEquals(2500.0, response.getPrecio());
        assertEquals(100, response.getStock());

        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoMedicamentoNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                MedicamentoNotFoundException.class,
                () -> service.buscarPorId(99L));

        verify(repository).findById(99L);
    }
}