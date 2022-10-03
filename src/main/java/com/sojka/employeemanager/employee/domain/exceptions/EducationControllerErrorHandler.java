package com.sojka.employeemanager.employee.domain.exceptions;

import com.sojka.employeemanager.employee.controller.EducationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = EducationController.class)
@Slf4j
public class EducationControllerErrorHandler {

    @ExceptionHandler({DuplicateKeyException.class, DuplicatedEducationException.class})
    ResponseEntity<EmployeeErrorResponse> handleDuplicateEmployeeException(DuplicateKeyException e) {
        String message = "The employee already have such degree: " + e.getMessage();
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.CONFLICT);
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoEducationException.class)
    ResponseEntity<EmployeeErrorResponse> handleNoEducationException(NoEducationException e) {
        String message = "The employee with ID " + e.getMessage() + " have no university degree.";
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
