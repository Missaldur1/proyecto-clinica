package com.clinic.msnotificaciones.dto;

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
public class NotificacionResponseDTO {

    private Long id;
    private String destinatario;
    private String titulo;
    private String mensaje;
    private String tipo;
    private String estado;
}