package com.clinic.msmedicos.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {

        super("No tiene permisos para acceder a este recurso");
    }

    public ForbiddenException(String mensaje) {

        super(mensaje);
    }
}