# Exception Handling Documentation

## Overview
This document describes the exception handling mechanism implemented in the project. It covers the `GlobalExceptionHandler`, the custom `BusinessException`, and the `OwnFieldError` class used for validation error representation.

---

## Classes Overview

### 1. **GlobalExceptionHandler**
The `GlobalExceptionHandler` is a centralized handler for exceptions, annotated with `@ControllerAdvice`. It intercepts exceptions thrown in the application and maps them to appropriate HTTP responses.

#### Methods:

- **`handleBusinessException(BusinessException ex)`**
  - Handles custom business exceptions.
  - If the exception contains `fieldErrors`, they are returned with the corresponding `HttpStatus`.
  - If the exception contains a general `error` map, it is returned with the `HttpStatus`.
  - If neither is present, a generic error message is returned with `500 INTERNAL_SERVER_ERROR`.

  **Response Examples:**
  - **Field Errors:**
    ```json
    [
      {
        "field": "fieldName",
        "message": "Field validation message"
      }
    ]
    ```
  - **General Error:**
    ```json
    {
      "message": "A general error message"
    }
    ```

- **`handleValidationExceptions(MethodArgumentNotValidException ex)`**
  - Handles validation errors from request bodies.
  - Maps `FieldError` objects to the custom `OwnFieldError` format and returns them with `400 BAD_REQUEST`.

  **Response Example:**
  ```json
  [
    {
      "field": "fieldName",
      "message": "Validation error message"
    }
  ]
  ```

- **`mapFieldError(FieldError fieldError)`**
  - Helper method that converts Spring's `FieldError` to the custom `OwnFieldError` structure.

---

### 2. **BusinessException**
The `BusinessException` is a custom runtime exception designed to handle both field-specific errors and general errors.

#### Fields:
- **`fieldErrors` (List<OwnFieldError>):**
  Represents a list of field-specific validation errors.
- **`error` (Map<String, String>):**
  Represents a general error message.
- **`status` (HttpStatus):**
  The HTTP status to return when this exception is thrown.

#### Constructors:
- **`BusinessException(String message, HttpStatus status)`**
  - Creates a general error with a message and status.
- **`BusinessException(String field, String message, HttpStatus status)`**
  - Creates an error for a single field.
- **`BusinessException(List<OwnFieldError> fieldErrors, HttpStatus status)`**
  - Creates errors for multiple fields.
- **`BusinessException(Map<String, String> error, HttpStatus status)`**
  - Creates a general error with a map of error details.

---

### 3. **OwnFieldError**
The `OwnFieldError` class represents field-specific errors in a structured format.

#### Fields:
- **`field` (String):**
  The name of the field where the error occurred.
- **`message` (String):**
  A human-readable message describing the error.

#### Example Usage:
```java
OwnFieldError fieldError = OwnFieldError.builder()
    .field("email")
    .message("Email is invalid")
    .build();
```

---

## Exception Handling Flow

1. **Validation Error Handling**:
   - When a `MethodArgumentNotValidException` is thrown (e.g., during request body validation), the `handleValidationExceptions` method in `GlobalExceptionHandler` is invoked.
   - The method extracts field errors, maps them to `OwnFieldError` objects, and returns them with a `400 BAD_REQUEST` status.

2. **Business Logic Error Handling**:
   - When a `BusinessException` is thrown, the `handleBusinessException` method in `GlobalExceptionHandler` is invoked.
   - Depending on the content of the exception (field errors, general error, or none), the method returns an appropriate response with the status defined in the exception.

3. **Generic Error Handling**:
   - If no specific error details are provided, a generic error message is returned with a `500 INTERNAL_SERVER_ERROR` status.

---

## Example Scenarios

### Scenario 1: Field Validation Error
**Input:**
```json
{
  "email": "invalid-email"
}
```
**Response:**
```json
[
  {
    "field": "email",
    "message": "Email is invalid"
  }
]
```

### Scenario 2: Business Logic Error
**Code:**
```java
throw new BusinessException("Unauthorized access", HttpStatus.UNAUTHORIZED);
```
**Response:**
```json
{
  "message": "Unauthorized access"
}
```

---

## Best Practices
- Ensure all custom exceptions extend `RuntimeException` to maintain consistent exception handling.
- Use meaningful and localized error messages for better client-side interpretation.
- Leverage `HttpStatus` enums to provide appropriate HTTP response codes.
- Centralize exception handling in a `@ControllerAdvice` class to avoid duplication and improve maintainability.

---

