package com.clinic.msreservas.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(
                        Exception ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationErrors(
                        MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult()
                                .getAllErrors()
                                .forEach(error -> {

                                        String field = ((FieldError) error).getField();

                                        String message = error.getDefaultMessage();

                                        errors.put(field, message);
                                });

                return ResponseEntity
                                .badRequest()
                                .body(errors);
        }

        @ExceptionHandler(ReservaNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleReservaNotFound(
                        ReservaNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(ReservaDuplicadaException.class)
        public ResponseEntity<ErrorResponse> handleReservaDuplicada(
                        ReservaDuplicadaException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CONFLICT.value())
                                .error("Conflict")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(error);
        }

        @ExceptionHandler(PacienteNotFoundException.class)
        public ResponseEntity<ErrorResponse> handlePacienteNotFound(
                        PacienteNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(MedicoNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleMedicoNotFound(
                        MedicoNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDenied(
                        org.springframework.security.access.AccessDeniedException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.FORBIDDEN.value())
                                .error("Forbidden")
                                .message("No tiene permisos para acceder a este recurso")
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(error);
        }

        @ExceptionHandler(ReservaBusinessException.class)
        public ResponseEntity<ErrorResponse> handleBusinessException(
                        ReservaBusinessException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Business Rule Violation")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

}