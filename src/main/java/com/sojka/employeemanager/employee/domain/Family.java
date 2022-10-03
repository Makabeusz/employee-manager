package com.sojka.employeemanager.employee.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Family {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String kinship;
    private String birthDate;

}
