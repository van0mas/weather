package org.example.exception;

public class SessionLimitException extends RuntimeException {
    public SessionLimitException(String message) {
        super(message);
    }
}
