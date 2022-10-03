package com.sojka.employeemanager.employee.domain.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateEmployeeException extends DuplicateKeyException {

    private static final long serialVersionUID = -7224068699794107920L;

    public DuplicateEmployeeException(String msg) {
        super(msg);
    }
}
