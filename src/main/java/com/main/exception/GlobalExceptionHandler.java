package com.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateDriverException.class)
    public ResponseEntity<?> handleDuplicateDriver(DuplicateDriverException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "status", HttpStatus.CONFLICT.value(),
                        "error", ex.getMessage()
                ));
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<String> handleDriverNotFound(DriverNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
