package com.clinic.mspacientes.dto;

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
