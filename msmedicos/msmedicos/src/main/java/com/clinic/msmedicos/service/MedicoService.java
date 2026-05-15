package com.clinic.msmedicos.service;
import java.util.List;

import com.clinic.msmedicos.dto.MedicoRequestDTO;
import com.clinic.msmedicos.dto.MedicoResponseDTO;

public interface MedicoService {

    List<MedicoResponseDTO> listar();

    MedicoResponseDTO buscarPorId(Long id);

    MedicoResponseDTO guardar(MedicoRequestDTO dto);

    MedicoResponseDTO actualizar(Long id, MedicoRequestDTO dto);

    void eliminar(Long id);
}