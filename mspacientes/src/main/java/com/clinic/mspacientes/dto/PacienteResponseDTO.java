package com.clinic.mspacientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Información completa de un paciente")
public class PacienteResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String prevision;
    private String telefono;
    private String email;

}
