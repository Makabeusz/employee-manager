package com.sojka.employeemanager.employee.domain.exceptions;

public class DuplicatedEducationException extends RuntimeException {
    public DuplicatedEducationException(String message) {
        super(message);
    }
}
