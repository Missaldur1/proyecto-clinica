package com.clinic.msreservas.exception;

public class ReservaDuplicadaException
        extends RuntimeException {

    public ReservaDuplicadaException() {

        super("Horario ya reservado");

    }

}