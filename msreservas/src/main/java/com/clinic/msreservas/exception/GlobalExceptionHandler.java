package com.clinic.msreservas.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(ReservaNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "No encontrado");
        response.put("mensaje", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ReservaDuplicadaException.class)
    public ResponseEntity<Map<String, String>> duplicada(ReservaDuplicadaException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Solicitud incorrecta");
        response.put("mensaje", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePacienteNotFound(PacienteNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Paciente no encontrado");
        response.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MedicoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMedicoNotFound(MedicoNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Médico no encontrado");
        response.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}