package com.clinic.msfarmacia.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String laboratorio;
    private Double precio;
    private Integer stock;
    private Boolean disponible;
}
