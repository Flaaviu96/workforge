package dev.workforge.app.WorkForge.handler;

import dev.workforge.app.WorkForge.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDetails> handleProjectNotFoundException(AppException exception){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatus(exception.getStatus().value());
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }
}
