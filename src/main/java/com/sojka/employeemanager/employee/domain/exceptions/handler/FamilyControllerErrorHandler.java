package com.sojka.employeemanager.employee.domain.exceptions.handler;

import com.sojka.employeemanager.employee.controller.EducationController;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeErrorResponse;
import com.sojka.employeemanager.employee.domain.exceptions.NoChildrenException;
import com.sojka.employeemanager.employee.domain.exceptions.NoFamilyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = EducationController.class)
@Slf4j
public class FamilyControllerErrorHandler {

//    @ExceptionHandler({DuplicateKeyException.class, DuplicatedEducationException.class})
//    ResponseEntity<EmployeeErrorResponse> handleDuplicateEmployeeException(DuplicateKeyException e) {
//        String message = "The employee already have such degree: " + e.getMessage();
//        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.CONFLICT);
//        log.warn(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//    }
//
    @ExceptionHandler(NoFamilyException.class)
    ResponseEntity<EmployeeErrorResponse> handleNoEducationException(NoFamilyException e) {
        String kinship = "family";
        if (e instanceof NoChildrenException) kinship = "children";
        String message = "The employee with ID " + e.getMessage() + " have no " + kinship + ".";
        EmployeeErrorResponse response = new EmployeeErrorResponse(message, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}