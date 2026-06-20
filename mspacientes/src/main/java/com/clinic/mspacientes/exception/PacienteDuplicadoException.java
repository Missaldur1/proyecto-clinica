package com.clinic.mspacientes.exception;

public class PacienteDuplicadoException extends RuntimeException {

    public PacienteDuplicadoException() {
        super("Ya existe un paciente registrado con ese RUT");
    }

    public PacienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}