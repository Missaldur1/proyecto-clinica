package com.clinic.msreservas.exception;

public class FechaReservaInvalidaException
        extends RuntimeException {

    public FechaReservaInvalidaException() {

        super(
            "No se pueden crear reservas en fechas pasadas");
    }
}