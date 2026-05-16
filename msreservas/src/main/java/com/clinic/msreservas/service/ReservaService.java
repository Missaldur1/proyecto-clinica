package com.clinic.msreservas.service;

import com.clinic.msreservas.dto.*;
import java.util.List;

public interface ReservaService {

    ReservaResponseDTO crear(ReservaRequestDTO dto);

    List<ReservaResponseDTO> listar();

    ReservaResponseDTO buscarPorId(Long id);

    ReservaResponseDTO actualizar(Long id, ReservaRequestDTO dto);

    void eliminar(Long id);

}