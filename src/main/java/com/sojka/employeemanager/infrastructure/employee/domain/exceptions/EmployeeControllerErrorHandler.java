package com.sojka.employeemanager.infrastructure.employee.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.sojka.employeemanager.infrastructure.employee.controller")
@Slf4j
public class EmployeeControllerErrorHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    ResponseEntity<EmployeeErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        EmployeeErrorResponse response = new EmployeeErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<EmployeeErrorResponse> handleDuplicateEmployeeException(DuplicateKeyException e) {
        String message = "Such employee already exists: " + e.getMessage();
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.CONFLICT);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<EmployeeErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getFieldError().getDefaultMessage();
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.BAD_REQUEST);
        log.warn(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
