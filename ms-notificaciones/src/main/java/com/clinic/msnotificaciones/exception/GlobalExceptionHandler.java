package com.clinic.msnotificaciones.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificacionNotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotificacionNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err ->
                        errores.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}