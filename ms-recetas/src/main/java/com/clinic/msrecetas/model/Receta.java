package com.clinic.msrecetas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recetas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Long medicoId;

    @Column(nullable = false)
    private String medicamento;

    @Column(nullable = false)
    private String dosis;

    @Column(nullable = false)
    private String indicaciones;

    @Column(nullable = false)
    private LocalDate fechaEmision;

    @Column(nullable = false)
    private Boolean activa;
}