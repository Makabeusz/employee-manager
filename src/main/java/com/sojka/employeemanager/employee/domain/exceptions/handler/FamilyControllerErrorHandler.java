package com.sojka.employeemanager.employee.domain.exceptions.handler;

import com.sojka.employeemanager.employee.controller.FamilyController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackageClasses = FamilyController.class)
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


}