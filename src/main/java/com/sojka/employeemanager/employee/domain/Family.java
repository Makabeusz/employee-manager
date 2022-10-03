package com.sojka.employeemanager.employee.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Family implements DomainObject {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String kinship;
    private String birthDate;

    @Override
    public String getObjectId() {
        return this.id + " " + this.firstName + " " + this.kinship;
    }
}
