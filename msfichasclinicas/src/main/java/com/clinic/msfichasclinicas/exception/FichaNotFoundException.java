package com.clinic.msfichasclinicas.exception;

public class FichaNotFoundException
        extends RuntimeException {

    public FichaNotFoundException() {

        super(
                "Ficha clínica no encontrada");

    }

}