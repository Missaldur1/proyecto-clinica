package com.clinic.msrecetas.exception;

public class RecetaNotFoundException extends RuntimeException {

    public RecetaNotFoundException(String mensaje) {
        super(mensaje);
    }
}