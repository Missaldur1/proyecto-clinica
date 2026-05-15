package com.clinic.msexamenes.exception;

public class ExamenNotFoundException
        extends RuntimeException {

    public ExamenNotFoundException() {

        super(
                "Examen no encontrado");

    }

}