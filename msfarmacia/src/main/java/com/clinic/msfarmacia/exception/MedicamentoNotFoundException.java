package com.clinic.msfarmacia.exception;

public class MedicamentoNotFoundException
        extends RuntimeException {

    public MedicamentoNotFoundException() {

        super("Medicamento no encontrado");
    }

    public MedicamentoNotFoundException(
            String mensaje) {

        super(mensaje);
    }
}