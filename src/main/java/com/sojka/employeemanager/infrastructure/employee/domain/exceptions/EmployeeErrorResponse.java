package com.sojka.employeemanager.infrastructure.employee.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EmployeeErrorResponse {

    private final String message;
    private final HttpStatus status;
}
