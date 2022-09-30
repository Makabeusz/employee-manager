package com.sojka.employeemanager.infrastructure.employee.domain.exceptions;


import java.util.NoSuchElementException;

public class EmployeeNotFoundException extends NoSuchElementException {

    public EmployeeNotFoundException(String s) {
        super("Employee with id " + s + " do not exist.");
    }
}
