package com.clinic.msreservas.exception;

public class HorarioReservaInvalidoException
        extends RuntimeException {

    public HorarioReservaInvalidoException() {

        super(
            "Horario fuera del rango de atención");
    }
}