package com.clinic.msfarmacia.exception;

public class MedicamentoDuplicadoException extends RuntimeException {

    public MedicamentoDuplicadoException() {
        super("Ya existe un medicamento registrado con esos datos");
    }

    public MedicamentoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}