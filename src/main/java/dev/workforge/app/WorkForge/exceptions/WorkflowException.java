package dev.workforge.app.WorkForge.exceptions;

import org.springframework.http.HttpStatus;

public class WorkflowException extends AppException {
    public WorkflowException(String message, HttpStatus status) {
        super(message, status);
    }
}
