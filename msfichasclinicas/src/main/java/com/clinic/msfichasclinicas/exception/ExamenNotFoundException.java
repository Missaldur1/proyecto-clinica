package com.clinic.msfichasclinicas.exception;

public class ExamenNotFoundException
        extends RuntimeException {

    public ExamenNotFoundException() {

        super("El examen asociado no existe");
    }

    public ExamenNotFoundException(String mensaje) {

        super(mensaje);
    }
}