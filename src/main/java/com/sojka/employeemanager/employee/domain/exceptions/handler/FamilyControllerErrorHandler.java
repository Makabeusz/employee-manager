package com.sojka.employeemanager.employee.domain.exceptions.handler;

import com.sojka.employeemanager.employee.controller.FamilyController;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicatedFamilyException;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeErrorResponse;
import com.sojka.employeemanager.employee.domain.exceptions.NoFamilyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = FamilyController.class)
@Slf4j
public class FamilyControllerErrorHandler {

    @ExceptionHandler({DuplicateKeyException.class, DuplicatedFamilyException.class})
    ResponseEntity<EmployeeErrorResponse> handleDuplicateEmployeeException(DuplicateKeyException e) {
        String message = "The employee family member already exists: " + e.getMessage();
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.CONFLICT);
        log.warn(message);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoFamilyException.class)
    ResponseEntity<EmployeeErrorResponse> handleNoFamilyException(NoFamilyException e) {
        String message = "The family member cannot be deleted, because do not exists: " + e.getMessage();
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.NOT_FOUND);
        log.warn(message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}