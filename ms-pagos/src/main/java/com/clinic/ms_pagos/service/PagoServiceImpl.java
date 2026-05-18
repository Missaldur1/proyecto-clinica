package com.clinic.ms_pagos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.ms_pagos.dto.PagoRequestDTO;
import com.clinic.ms_pagos.dto.PagoResponseDTO;
import com.clinic.ms_pagos.exception.PagoNotFoundException;
import com.clinic.ms_pagos.model.Pago;
import com.clinic.ms_pagos.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repository;

    @Override
    public List<PagoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public PagoResponseDTO buscarPorId(Long id) {

        Pago pago = repository.findById(id)
                .orElseThrow(() ->
                        new PagoNotFoundException(
                                "Pago no encontrado"));

        return mapToResponseDTO(pago);
    }

    @Override
    public PagoResponseDTO guardar(
            PagoRequestDTO dto) {

        Pago pago = Pago.builder()
                .pacienteId(dto.getPacienteId())
                .concepto(dto.getConcepto())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .estado(dto.getEstado())
                .fechaPago(dto.getFechaPago())
                .build();

        return mapToResponseDTO(repository.save(pago));
    }

    @Override
    public PagoResponseDTO actualizar(
            Long id,
            PagoRequestDTO dto) {

        Pago pago = repository.findById(id)
                .orElseThrow(() ->
                        new PagoNotFoundException(
                                "Pago no encontrado"));

        pago.setPacienteId(dto.getPacienteId());
        pago.setConcepto(dto.getConcepto());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado());
        pago.setFechaPago(dto.getFechaPago());

        return mapToResponseDTO(repository.save(pago));
    }

    @Override
    public void eliminar(Long id) {

        Pago pago = repository.findById(id)
                .orElseThrow(() ->
                        new PagoNotFoundException(
                                "Pago no encontrado"));

        repository.delete(pago);
    }

    private PagoResponseDTO mapToResponseDTO(
            Pago pago) {

        return PagoResponseDTO.builder()
                .id(pago.getId())
                .pacienteId(pago.getPacienteId())
                .concepto(pago.getConcepto())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodoPago())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .build();
    }
}