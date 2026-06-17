package com.clinic.msusuarios.dto;

import com.clinic.msusuarios.enums.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UsuarioRequestDTO {
@NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Formato email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private Rol rol;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}
