package dev.workforge.app.WorkForge.exceptions;

import org.springframework.http.HttpStatus;

public class StateTransitionException extends AppException {
    public StateTransitionException(String message, HttpStatus status) {
        super(message, status);
    }
}
