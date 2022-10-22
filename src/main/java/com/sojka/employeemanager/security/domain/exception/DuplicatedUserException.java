package com.sojka.employeemanager.security.domain.exception;

public class DuplicatedUserException extends RuntimeException {

    private static final long serialVersionUID = 5155310472745510858L;

    public DuplicatedUserException(String message) {
        super(message);
    }
}
