package com.sojka.employeemanager.employee.domain.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class DuplicatedEducationException extends DuplicateKeyException {

    private static final long serialVersionUID = -6350257348347334018L;

    public DuplicatedEducationException(String message) {
        super(message);
    }
}
