package com.clinic.msexamenes.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ExamenNotFoundException.class)

        public ResponseEntity<String> notFound(Exception ex) {

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(ex.getMessage());

        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> error(Exception ex) {

                return ResponseEntity
                                .status(
                                                HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(
                                                "Error interno servidor");

        }

}