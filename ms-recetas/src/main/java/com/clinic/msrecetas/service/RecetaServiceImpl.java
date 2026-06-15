package com.clinic.msrecetas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clinic.msrecetas.client.MedicoClient;
import com.clinic.msrecetas.client.PacienteClient;
import com.clinic.msrecetas.dto.RecetaRequestDTO;
import com.clinic.msrecetas.dto.RecetaResponseDTO;
import com.clinic.msrecetas.exception.MedicoNotFoundException;
import com.clinic.msrecetas.exception.PacienteNotFoundException;
import com.clinic.msrecetas.exception.RecetaNotFoundException;
import com.clinic.msrecetas.exception.ReglaNegocioException;
import com.clinic.msrecetas.mapper.RecetaMapper;
import com.clinic.msrecetas.model.Receta;
import com.clinic.msrecetas.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {

        private final RecetaRepository repository;

        private final PacienteClient pacienteClient;

        private final MedicoClient medicoClient;

        @Override
        public List<RecetaResponseDTO> listar() {

                log.info("Listando recetas");

                return repository.findAll()
                                .stream()
                                .map(RecetaMapper::toDTO)
                                .toList();
        }

        @Override
        public RecetaResponseDTO buscarPorId(Long id) {

                log.info("Buscando receta ID: {}", id);

                Receta receta = repository.findById(id)
                                .orElseThrow(() -> {

                                        log.error(
                                                        "Receta no encontrada ID {}",
                                                        id);

                                        return new RecetaNotFoundException();
                                });

                return RecetaMapper.toDTO(receta);
        }

        @Override
        public RecetaResponseDTO guardar(
                        RecetaRequestDTO dto) {

                log.info(
                                "Creando receta para paciente ID: {}",
                                dto.getPacienteId());

                // RN01: validar existencia paciente
                try {

                        pacienteClient.buscarPaciente(
                                        dto.getPacienteId());

                } catch (Exception e) {

                        log.error(
                                        "Paciente {} no encontrado",
                                        dto.getPacienteId());

                        throw new PacienteNotFoundException();
                }

                // RN02: validar existencia médico
                try {

                        medicoClient.buscarMedico(
                                        dto.getMedicoId());

                } catch (Exception e) {

                        log.error(
                                        "Médico {} no encontrado",
                                        dto.getMedicoId());

                        throw new MedicoNotFoundException();
                }

                // RN03: medicamento obligatorio
                if (dto.getMedicamento() == null
                                || dto.getMedicamento().trim().isEmpty()) {

                        throw new ReglaNegocioException(
                                        "La receta debe contener un medicamento");
                }

                Receta receta = RecetaMapper.toEntity(dto);

                // RN04: fecha automática
                receta.setFechaEmision(
                                LocalDate.now());

                // RN05: toda receta nueva inicia activa
                receta.setActiva(true);

                Receta guardada = repository.save(receta);

                log.info(
                                "Receta creada correctamente ID: {}",
                                guardada.getId());

                return RecetaMapper.toDTO(
                                guardada);
        }

        @Override
        public RecetaResponseDTO actualizar(
                        Long id,
                        RecetaRequestDTO dto) {

                log.info(
                                "Actualizando receta ID: {}",
                                id);

                Receta receta = repository.findById(id)
                                .orElseThrow(() -> {

                                        log.error(
                                                        "Receta no encontrada ID {}",
                                                        id);

                                        return new RecetaNotFoundException();
                                });

                // RN: medicamento obligatorio
                if (dto.getMedicamento() == null
                                || dto.getMedicamento().trim().isEmpty()) {

                        throw new ReglaNegocioException(
                                        "La receta debe contener un medicamento");
                }

                receta.setPacienteId(
                                dto.getPacienteId());

                receta.setMedicoId(
                                dto.getMedicoId());

                receta.setMedicamento(
                                dto.getMedicamento());

                receta.setDosis(
                                dto.getDosis());

                receta.setIndicaciones(
                                dto.getIndicaciones());

                // NO modificar fechaEmision
                // receta.setFechaEmision(...)

                receta.setActiva(
                                dto.getActiva());

                Receta actualizada = repository.save(receta);

                log.info(
                                "Receta actualizada correctamente ID: {}",
                                id);

                return RecetaMapper.toDTO(
                                actualizada);
        }

        @Override
        public void eliminar(Long id) {

                log.info(
                                "Eliminando receta ID: {}",
                                id);

                Receta receta = repository.findById(id)
                                .orElseThrow(() -> {

                                        log.error(
                                                        "Receta no encontrada ID {}",
                                                        id);

                                        return new RecetaNotFoundException();
                                });

                repository.delete(receta);

                log.info(
                                "Receta eliminada correctamente ID: {}",
                                id);
        }
}