package com.clinic.mspacientes.dto;
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

public class PacienteRequestDTO {
    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "Edad inválida")
    private Integer edad;

    @NotBlank(message = "La prevision es obligatoria")
    private String prevision;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;


}
