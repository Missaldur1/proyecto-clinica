package com.clinic.msrecetas.exception;

public class RecetaInactivaException
        extends RuntimeException {

    public RecetaInactivaException() {

        super(
                "No se puede modificar una receta inactiva");
    }
}