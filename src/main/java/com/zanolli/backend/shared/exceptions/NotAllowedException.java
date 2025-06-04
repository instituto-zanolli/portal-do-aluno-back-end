package com.zanolli.backend.shared.exceptions;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String message) {
        super(message);
    }
}
