package com.sojka.employeemanager.employee.domain.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class DuplicatedEducationException extends DuplicateKeyException {
    public DuplicatedEducationException(String message) {
        super(message);
    }
}
