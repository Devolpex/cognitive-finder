package org.cognitivefinder.tracking.errors;

import java.util.List;
import java.util.stream.Collectors;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        // Check if there are field errors or a general error
        if (ex.getFieldErrors() != null && !ex.getFieldErrors().isEmpty()) {
            // If there are field errors, return them with the status
            return new ResponseEntity<>(ex.getFieldErrors(), ex.getStatus());
        } else if (ex.getError() != null && !ex.getError().isEmpty()) {
            // If there is a general error, return the error message and status
            return new ResponseEntity<>(ex.getError(), ex.getStatus());
        } else {
            // If no specific error, return a generic response
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<OwnFieldError>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<OwnFieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
    }

    // Helper method to map field errors
    private OwnFieldError mapFieldError(FieldError fieldError) {
        return OwnFieldError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }

}
