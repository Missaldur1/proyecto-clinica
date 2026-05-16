package com.clinic.msfichasclinicas.service;

import java.util.List;

import com.clinic.msfichasclinicas.dto.*;

public interface FichaClinicaService {

    FichaClinicaResponseDTO crear(
            FichaClinicaRequestDTO dto);

    List<FichaClinicaResponseDTO> listar();

    FichaClinicaResponseDTO buscarPorId(Long id);

    FichaClinicaResponseDTO actualizar(
            Long id,
            FichaClinicaRequestDTO dto);

    void eliminar(Long id);

}