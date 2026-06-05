package com.clinic.msreservas.exception;

public class MedicoNotFoundException extends RuntimeException {

    public MedicoNotFoundException() {

        super("El médico asociado no existe");
    }

    public MedicoNotFoundException(String mensaje) {

        super(mensaje);
    }

}