package com.clinic.msmedicos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Información necesaria para registrar un médico")
public class MedicoRequestDTO {

    @NotBlank(message = "El rut es obligatorio")
    @Schema(description = "RUN del médico", example = "12345678-9")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del médico", example = "Pedro")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del médico", example = "González")
    private String apellido;

    @NotBlank(message = "La especialidad es obligatoria")
    @Schema(description = "Especialidad médica", example = "Cardiología")
    private String especialidad;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Schema(description = "Correo electrónico", example = "pedro.gonzalez@clinica.cl")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Schema(description = "Número de teléfono", example = "912345678")
    private String telefono;

    @NotNull(message = "Debe indicar disponibilidad")
    @Schema(description = "Indica si el médico está disponible para recibir reservas", example = "true")
    private Boolean disponible;
}