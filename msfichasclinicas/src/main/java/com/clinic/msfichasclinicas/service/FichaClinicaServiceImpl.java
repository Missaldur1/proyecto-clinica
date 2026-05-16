package com.clinic.msfichasclinicas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msfichasclinicas.client.*;
import com.clinic.msfichasclinicas.dto.*;
import com.clinic.msfichasclinicas.exception.*;
import com.clinic.msfichasclinicas.mapper.FichaClinicaMapper;
import com.clinic.msfichasclinicas.model.FichaClinica;
import com.clinic.msfichasclinicas.repository.FichaRepository;
import com.clinic.msfichasclinicas.service.FichaClinicaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FichaClinicaServiceImpl
        implements FichaClinicaService {

    private final FichaRepository repository;

    private final PacienteClient pacienteClient;

    private final MedicoClient medicoClient;

    private final ExamenClient examenClient;

    @Override
    public List<FichaClinicaResponseDTO> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    @Override
    public FichaClinicaResponseDTO buscarPorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public FichaClinicaResponseDTO actualizar(Long id, FichaClinicaRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public FichaClinicaResponseDTO crear(FichaClinicaRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

}
