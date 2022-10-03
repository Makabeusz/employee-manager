package com.sojka.employeemanager.employee.domain.exceptions;

public class NoChildrenException extends NoFamilyException {

    private static final long serialVersionUID = -8118957254351664130L;

    public NoChildrenException(String message) {
        super(message);
    }
}
