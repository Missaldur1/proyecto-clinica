package com.clinic.ms_pagos.service;

import java.util.List;

import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;

public interface PagoService {

    List<PagoResponseDTO> listar();

    PagoResponseDTO buscarPorId(Long id);

    PagoResponseDTO guardar(PagoRequestDTO dto);

    PagoResponseDTO actualizar(Long id,
                               PagoRequestDTO dto);

    void eliminar(Long id);
}