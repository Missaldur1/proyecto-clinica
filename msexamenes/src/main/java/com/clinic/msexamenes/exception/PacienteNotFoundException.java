package com.clinic.msexamenes.exception;

public class PacienteNotFoundException
        extends RuntimeException {

    public PacienteNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}