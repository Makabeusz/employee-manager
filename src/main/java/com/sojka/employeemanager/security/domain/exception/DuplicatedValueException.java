package com.sojka.employeemanager.security.domain.exception;

public class DuplicatedValueException extends RuntimeException {

    private static final long serialVersionUID = 9073064089804889750L;

    public DuplicatedValueException(String message) {
        super(message);
    }
}
