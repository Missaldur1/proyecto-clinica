package com.clinic.ms_pagos.exception;

public class PacienteNotFoundException
        extends RuntimeException {

    public PacienteNotFoundException() {

        super(
                "El paciente indicado no existe");
    }
}