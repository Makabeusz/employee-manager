package com.sojka.employeemanager.employee.domain.exceptions;

public class NoEducationException extends RuntimeException {

    private static final long serialVersionUID = -8647602744928489629L;

    public NoEducationException(String message) {
        super(message);
    }
}
