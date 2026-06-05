package com.clinic.msfichasclinicas.exception;

public class PacienteNotFoundException
        extends RuntimeException {

    public PacienteNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}