package dev.workforge.app.WorkForge.exceptions;

import org.springframework.http.HttpStatus;

public class TaskException extends AppException {
    public TaskException(String message, HttpStatus status) {
        super(message, status);
    }
}
