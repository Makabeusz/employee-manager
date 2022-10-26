package com.sojka.employeemanager.security.domain.exception;

public class DuplicatedEmailException extends DuplicatedValueException {

    private static final long serialVersionUID = 4508496000256481195L;

    public DuplicatedEmailException(String message) {
        super("User with email " + message + " already exits.");
    }
}
