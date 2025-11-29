package dev.workforge.app.WorkForge.exceptions;

import org.springframework.http.HttpStatus;

public class PermissionException extends AppException {
    public PermissionException(String message, HttpStatus status) {
        super(message, status);
    }
}
