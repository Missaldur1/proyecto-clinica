package com.clinic.msfarmacia.exception;
public class MedicamentoNotFoundException extends RuntimeException {

    public MedicamentoNotFoundException(String mensaje) {
        super(mensaje);
    }

}
