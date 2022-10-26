package com.sojka.employeemanager.security.domain.exception;

public class DuplicatedUserException extends DuplicatedValueException {

    private static final long serialVersionUID = 5155310472745510858L;

    public DuplicatedUserException(String message) {
        super("Username " + message + " already exits.");
    }
}
