package com.clinic.msrecetas.exception;

public class PacienteNotFoundException
        extends RuntimeException {

    public PacienteNotFoundException() {

        super("Paciente no encontrado");
    }
}