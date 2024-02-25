package com.company.apps.exception;

public class PlayerBusinessException extends RuntimeException {
    public PlayerBusinessException() {
    }

    public PlayerBusinessException(String message) {
        super(message);
    }

    public PlayerBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}