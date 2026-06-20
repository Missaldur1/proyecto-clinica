package com.clinic.msusuarios.exception;

public class InvalidCredentialsException
        extends RuntimeException {

    public InvalidCredentialsException() {

        super("Correo o contraseña incorrectos");
    }
}