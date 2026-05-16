package com.clinic.msreservas.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservaNotFoundException.class)

    public ResponseEntity<String> notFound(Exception ex) {

        return ResponseEntity
                .status(404)
                .body(ex.getMessage());

    }

    @ExceptionHandler(ReservaDuplicadaException.class)

    public ResponseEntity<String> duplicada(Exception ex) {

        return ResponseEntity
                .status(400)
                .body(ex.getMessage());

    }

}