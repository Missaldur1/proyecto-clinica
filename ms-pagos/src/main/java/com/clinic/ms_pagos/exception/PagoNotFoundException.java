package com.clinic.ms_pagos.exception;

public class PagoNotFoundException
        extends RuntimeException {

    public PagoNotFoundException() {

        super("Pago no encontrado");
    }

    public PagoNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}