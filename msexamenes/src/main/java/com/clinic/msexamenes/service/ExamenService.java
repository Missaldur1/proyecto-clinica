package com.clinic.msexamenes.service;

import java.util.List;

import com.clinic.msexamenes.dto.*;

public interface ExamenService {

    ExamenResponseDTO crear(
            ExamenRequestDTO dto);

    List<ExamenResponseDTO> listar();

    ExamenResponseDTO buscarPorId(Long id);

    ExamenResponseDTO actualizar(
            Long id,
            ExamenRequestDTO dto);

    void eliminar(Long id);

}