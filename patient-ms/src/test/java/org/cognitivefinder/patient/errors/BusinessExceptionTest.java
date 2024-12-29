package org.cognitivefinder.patient.errors;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.cognitivefinder.patient.errors.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class BusinessExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Test error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        BusinessException exception = new BusinessException(message, status);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatus());
        assertNotNull(exception.getError());
        assertEquals("Test error message", exception.getError().get("message"));
        assertNull(exception.getFieldErrors());
    }

    @Test
    void testConstructorWithFieldError() {
        String field = "testField";
        String message = "Field error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        BusinessException exception = new BusinessException(field, message, status);

        assertNotNull(exception.getFieldErrors());
        assertEquals(1, exception.getFieldErrors().size());
        assertEquals(field, exception.getFieldErrors().get(0).getField());
        assertEquals(message, exception.getFieldErrors().get(0).getMessage());
        assertEquals(status, exception.getStatus());
        assertNull(exception.getError());
    }

    @Test
    void testConstructorWithFieldErrorsList() {
        OwnFieldError fieldError1 = OwnFieldError.builder().field("field1").message("message1").build();
        OwnFieldError fieldError2 = OwnFieldError.builder().field("field2").message("message2").build();
        List<OwnFieldError> fieldErrors = List.of(fieldError1, fieldError2);
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        BusinessException exception = new BusinessException(fieldErrors, status);

        assertEquals(fieldErrors, exception.getFieldErrors());
        assertEquals(status, exception.getStatus());
        assertNull(exception.getError());
    }

    @Test
    void testConstructorWithErrorMap() {
        Map<String, String> errorMap = Map.of("key1", "value1", "key2", "value2");
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        BusinessException exception = new BusinessException(errorMap, status);

        assertEquals(errorMap, exception.getError());
        assertEquals(status, exception.getStatus());
        assertNull(exception.getFieldErrors());
    }
}
