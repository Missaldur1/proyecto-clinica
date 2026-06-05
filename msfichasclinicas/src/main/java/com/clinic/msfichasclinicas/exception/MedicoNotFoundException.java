package com.clinic.msfichasclinicas.exception;

public class MedicoNotFoundException
        extends RuntimeException {

    public MedicoNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}