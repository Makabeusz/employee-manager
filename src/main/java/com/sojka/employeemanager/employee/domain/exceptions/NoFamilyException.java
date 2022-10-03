package com.sojka.employeemanager.employee.domain.exceptions;

public class NoFamilyException extends RuntimeException {

    private static final long serialVersionUID = 7142950631654240502L;

    public NoFamilyException(String message) {
        super(message);
    }
}
