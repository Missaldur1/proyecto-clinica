package com.clinic.msfichasclinicas.exception;

public class ExamenNotFoundException
        extends RuntimeException {

    public ExamenNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}