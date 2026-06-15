package com.clinic.msrecetas.exception;

public class FechaRecetaInvalidaException
        extends RuntimeException {

    public FechaRecetaInvalidaException() {

        super(
                "La fecha de emisión no puede ser futura");
    }
}