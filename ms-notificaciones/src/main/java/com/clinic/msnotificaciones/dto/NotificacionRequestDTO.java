package com.clinic.msnotificaciones.dto;

import jakarta.validation.constraints.NotBlank;
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
public class NotificacionRequestDTO {

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}