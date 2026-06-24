package com.clinic.mspacientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
@Schema(description = "Información necesaria para registrar un paciente")
public class PacienteRequestDTO {
    @NotBlank(message = "El rut es obligatorio")
    @Schema(description = "RUN o documento de identidad", example = "12345678-9")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido", example = "Pérez")
    private String apellido;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "Edad inválida")
    @Schema(description = "Edad", example = "30")
    private Integer edad;

    @NotBlank(message = "La prevision es obligatoria")
    @Schema(description = "Tipo de previsión", example = "Fonasa")
    private String prevision;

    @NotBlank(message = "El teléfono es obligatorio")
    @Schema(description = "Número telefónico", example = "+56912345678")
    private String telefono;

    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Correo electrónico", example = "juan@gmail.com")
    private String email;

}
