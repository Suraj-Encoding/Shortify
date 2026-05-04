package com.shortify.exception;

import com.shortify.dto.AppError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler
 * Handles all exceptions and returns consistent error responses
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppError> handleAppException(AppException ex) {
        log.error("🚫 Application Error: {}", ex.getMessage());
        
        AppError error = AppError.of(ex.getMessage(), ex.getStatus().value());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> handleGenericException(Exception ex) {
        log.error("🚫 Unexpected Error: {}", ex.getMessage(), ex);
        
        AppError error = AppError.of(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
