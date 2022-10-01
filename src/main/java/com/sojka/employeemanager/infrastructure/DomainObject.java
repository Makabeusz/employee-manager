package com.sojka.employeemanager.infrastructure;

public interface DomainObject {

    String getObjectId();

    enum Type {
        EMPLOYEE,
        EDUCATION
    }
}
