package com.sojka.employeemanager.infrastructure.employee.domain;

import com.sojka.employeemanager.infrastructure.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements DomainObject {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String birthDate;
    private String personalId;

    @Override
    public String getObjectId() {
        return this.getPersonalId();
    }
}