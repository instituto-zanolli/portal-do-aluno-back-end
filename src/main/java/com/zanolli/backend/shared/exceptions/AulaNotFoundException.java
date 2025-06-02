package com.zanolli.backend.shared.exceptions;

public class AulaNotFoundException extends RuntimeException {
    public AulaNotFoundException(String message) {
        super(message);
    }
}
