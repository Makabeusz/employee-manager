package com.sojka.employeemanager.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
