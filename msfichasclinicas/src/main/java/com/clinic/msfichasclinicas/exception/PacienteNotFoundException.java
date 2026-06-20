package com.clinic.msfichasclinicas.exception;

public class PacienteNotFoundException
        extends RuntimeException {

    public PacienteNotFoundException() {

        super("El paciente asociado no existe");
    }

    public PacienteNotFoundException(String mensaje) {

        super(mensaje);
    }
}