package com.clinic.msrecetas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaRequestDTO {

    @NotNull(message = "El id del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El id del médico es obligatorio")
    private Long medicoId;

    @NotBlank(message = "El medicamento es obligatorio")
    private String medicamento;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotBlank(message = "Las indicaciones son obligatorias")
    private String indicaciones;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    @NotNull(message = "Debe indicar si la receta está activa")
    private Boolean activa;
}