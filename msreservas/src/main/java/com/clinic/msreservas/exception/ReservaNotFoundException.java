package com.clinic.msreservas.exception;

public class ReservaNotFoundException extends RuntimeException {

    public ReservaNotFoundException() {

        super("Reserva no encontrada");

    }

}