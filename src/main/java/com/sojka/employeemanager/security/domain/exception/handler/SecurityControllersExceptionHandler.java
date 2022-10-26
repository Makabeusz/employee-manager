package com.sojka.employeemanager.security.domain.exception.handler;

import com.sojka.employeemanager.employee.domain.exceptions.EmployeeErrorResponse;
import com.sojka.employeemanager.security.domain.exception.DuplicatedUserException;
import com.sojka.employeemanager.security.domain.exception.SecurityErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.sojka.employeemanager.security.controller")
@Slf4j
public class SecurityControllersExceptionHandler {

    @ExceptionHandler(DuplicatedUserException.class)
    ResponseEntity<SecurityErrorResponse> handleDuplicatedUserException(DuplicatedUserException e) {
        SecurityErrorResponse response = new SecurityErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<SecurityErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        SecurityErrorResponse response = new SecurityErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
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
