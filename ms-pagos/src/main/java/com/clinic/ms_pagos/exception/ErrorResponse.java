package com.clinic.ms_pagos.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;


/*Se crea ErrorResponse para manejar respuestas de error en microservicios */
@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}