package com.clinic.msmedicos.dto;

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
public class MedicoResponseDTO {

    private Long id;

    private String rut;

    private String nombre;

    private String apellido;

    private String especialidad;

    private String correo;

    private String telefono;

    private Boolean disponible;
}