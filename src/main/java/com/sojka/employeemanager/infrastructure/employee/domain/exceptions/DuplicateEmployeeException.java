package com.sojka.employeemanager.infrastructure.employee.domain.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateEmployeeException extends DuplicateKeyException {
    public DuplicateEmployeeException(String msg) {
        super("Such employee already exists: " + msg);
    }
}
