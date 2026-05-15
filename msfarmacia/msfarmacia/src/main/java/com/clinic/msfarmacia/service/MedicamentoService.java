package com.clinic.msfarmacia.service;

import com.clinic.msfarmacia.dto.*;

import java.util.List;

public interface MedicamentoService {

    List<MedicamentoResponseDTO> listar();

    MedicamentoResponseDTO buscarPorId(Long id);

    MedicamentoResponseDTO guardar(MedicamentoRequestDTO dto);

    MedicamentoResponseDTO actualizar(Long id,
            MedicamentoRequestDTO dto);

    void eliminar(Long id);
}
