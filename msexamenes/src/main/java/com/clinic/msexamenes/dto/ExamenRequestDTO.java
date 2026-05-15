package main.java.com.clinic.msexamenes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenRequestDTO {

    @NotNull
    private Long pacienteId;

    @NotBlank(message = "Tipo obligatorio")
    private String tipoExamen;

    @NotNull
    private LocalDate fecha;

    @NotBlank(message = "Resultado obligatorio")
    private String resultado;

    @NotBlank(message = "Estado obligatorio")
    private String estado;

}