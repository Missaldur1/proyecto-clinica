package com.clinic.msmedicos.exception;

public class MedicoNotFoundException
        extends RuntimeException {

    public MedicoNotFoundException() {

        super("Médico no encontrado");
    }

    public MedicoNotFoundException(
            Long id) {

        super("Médico no encontrado con id: " + id);
    }

    public MedicoNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}