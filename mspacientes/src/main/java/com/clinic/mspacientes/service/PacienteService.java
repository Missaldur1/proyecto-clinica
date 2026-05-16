package com.clinic.mspacientes.service;
import com.clinic.mspacientes.dto.*;

import java.util.List;

public interface PacienteService {

    PacienteResponseDTO crearPaciente(PacienteRequestDTO dto);

    List<PacienteResponseDTO> listarPacientes();

    PacienteResponseDTO buscarPorId(Long id);

    PacienteResponseDTO buscarPorRut(String rut);

    PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO dto);

    void eliminarPaciente(Long id);

}
