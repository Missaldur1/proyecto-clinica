package com.clinic.msrecetas.service;

import java.util.List;

import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;

public interface RecetaService {

    List<RecetaResponseDTO> listar();

    RecetaResponseDTO buscarPorId(Long id);

    RecetaResponseDTO guardar(RecetaRequestDTO dto);

    RecetaResponseDTO actualizar(Long id,
                                 RecetaRequestDTO dto);

    void eliminar(Long id);
}