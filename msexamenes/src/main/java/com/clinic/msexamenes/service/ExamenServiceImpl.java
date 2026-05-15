package com.clinic.msexamenes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msexamenes.client.PacienteClient;
import com.clinic.msexamenes.dto.*;

import com.clinic.msexamenes.repository.ExamenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository repository;

    private final PacienteClient pacienteClient;

    @Override
    public ExamenResponseDTO crear(ExamenRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    @Override
    public List<ExamenResponseDTO> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    @Override
    public ExamenResponseDTO buscarPorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public ExamenResponseDTO actualizar(Long id, ExamenRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
}