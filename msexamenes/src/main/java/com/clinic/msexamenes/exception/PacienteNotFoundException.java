package com.clinic.msexamenes.exception;

public class PacienteNotFoundException
        extends RuntimeException {

    public PacienteNotFoundException() {

        super("Paciente no encontrado");
    }

    public PacienteNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}