package com.sojka.employeemanager.employee.domain.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class DuplicatedFamilyException extends DuplicateKeyException {

    private static final long serialVersionUID = 4063691390964111714L;

    public DuplicatedFamilyException(String message) {
        super(message);
    }
}
