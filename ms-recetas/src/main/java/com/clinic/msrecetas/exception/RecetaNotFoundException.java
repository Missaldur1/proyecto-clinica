package com.clinic.msrecetas.exception;

public class RecetaNotFoundException
        extends RuntimeException {

    public RecetaNotFoundException() {

        super("Receta no encontrada");
    }

    public RecetaNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}