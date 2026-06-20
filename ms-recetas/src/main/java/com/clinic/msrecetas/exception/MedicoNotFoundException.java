package com.clinic.msrecetas.exception;

public class MedicoNotFoundException
        extends RuntimeException {

    public MedicoNotFoundException() {

        super("Médico no encontrado");
    }
}