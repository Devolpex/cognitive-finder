package org.cognitivefinder.microservice.errors.exception;

import java.util.List;
import java.util.Map;

import org.cognitivefinder.microservice.errors.OwnFieldError;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<OwnFieldError> fieldErrors;
    private Map<String, String> error;
    private HttpStatus status;


    // Constructor for message
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.error = Map.of("message", message);
    }

    // Constructor for one fieldError
    public BusinessException(String field, String message, HttpStatus status) {
        this.fieldErrors = List.of(OwnFieldError.builder().field(field).message(message).build());
        this.status = status;
    }

    // Constructor with fieldErrors
    public BusinessException(List<OwnFieldError> fieldErrors, HttpStatus status) {
        this.fieldErrors = fieldErrors;
        this.status = status;
    }

    // Constructor with error map
    public BusinessException(Map<String, String> error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public List<OwnFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public Map<String, String> getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }

}