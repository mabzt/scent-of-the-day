package com.maison.mabs.userservice.infrastructure.adapter.in.web.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
