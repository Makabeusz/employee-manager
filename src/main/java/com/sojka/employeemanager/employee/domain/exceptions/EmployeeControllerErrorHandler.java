package com.sojka.employeemanager.employee.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.sojka.employeemanager.employee.controller")
@Slf4j
public class EmployeeControllerErrorHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    ResponseEntity<EmployeeErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        EmployeeErrorResponse response = new EmployeeErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmployeeException.class)
    ResponseEntity<EmployeeErrorResponse> handleDuplicateEmployeeException(DuplicateEmployeeException e) {
        EmployeeErrorResponse response = new EmployeeErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
