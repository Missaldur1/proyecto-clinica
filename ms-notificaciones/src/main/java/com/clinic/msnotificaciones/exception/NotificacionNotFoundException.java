package com.clinic.msnotificaciones.exception;

public class NotificacionNotFoundException extends RuntimeException {

    public NotificacionNotFoundException(String mensaje) {
        super(mensaje);
    }
}