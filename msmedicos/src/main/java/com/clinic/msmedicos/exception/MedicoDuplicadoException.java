package com.clinic.msmedicos.exception;

public class MedicoDuplicadoException extends RuntimeException {

    public MedicoDuplicadoException() {
        super("Ya existe un médico registrado con esos datos");
    }

    public MedicoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}