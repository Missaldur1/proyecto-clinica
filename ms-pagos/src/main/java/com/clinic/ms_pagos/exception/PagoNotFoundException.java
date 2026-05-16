package com.clinic.ms_pagos.exception;

public class PagoNotFoundException extends RuntimeException {

    public PagoNotFoundException(String mensaje) {
        super(mensaje);
    }
}