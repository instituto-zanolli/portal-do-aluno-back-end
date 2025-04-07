package com.zanolli.backend.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ExceptionFilters> buildConflictResponse(String message) {
        ExceptionFilters exceptionFilters = new ExceptionFilters(
                message,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(exceptionFilters, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NaipeConflictException.class)
    public ResponseEntity<ExceptionFilters> handleNaipeConflict(NaipeConflictException exception) {
        return buildConflictResponse(exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ExceptionFilters> handleEmailNotFound(EmailNotFoundException exception) {
        return buildConflictResponse(exception.getMessage());
    }
    
    @ExceptionHandler(EmailConflictException.class)
    public ResponseEntity<ExceptionFilters> handleEmailConflict(EmailConflictException exception) {
        return buildConflictResponse(exception.getMessage());
    }
}
