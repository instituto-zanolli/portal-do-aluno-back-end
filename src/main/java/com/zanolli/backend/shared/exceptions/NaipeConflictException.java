package com.zanolli.backend.shared.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class NaipeConflictException extends RuntimeException {
    public NaipeConflictException(String message) {
        super(message);
    }
}
