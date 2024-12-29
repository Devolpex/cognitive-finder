package org.cognitivefinder.tracking.errors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleBusinessExceptionWithFieldErrors() {
        OwnFieldError fieldError = OwnFieldError.builder().field("field1").message("error1").build();
        BusinessException exception = new BusinessException(List.of(fieldError), HttpStatus.BAD_REQUEST);

        ResponseEntity<Object> response = handler.handleBusinessException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        assertEquals(1, ((List<?>) response.getBody()).size());
    }

    @Test
    void testHandleBusinessExceptionWithErrorMap() {
        Map<String, String> errorMap = Map.of("key", "value");
        BusinessException exception = new BusinessException(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<Object> response = handler.handleBusinessException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMap, response.getBody());
    }

    @Test
    void testHandleBusinessExceptionWithNoSpecificError() {
        BusinessException exception = new BusinessException("message", HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<Object> response = handler.handleBusinessException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("message", responseBody.get("message"));
    }

    @Test
    void testHandleValidationExceptions() {
        FieldError fieldError1 = new FieldError("objectName", "field1", "error message 1");
        FieldError fieldError2 = new FieldError("objectName", "field2", "error message 2");
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<List<OwnFieldError>> response = handler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("field1", response.getBody().get(0).getField());
        assertEquals("error message 1", response.getBody().get(0).getMessage());
    }
}
