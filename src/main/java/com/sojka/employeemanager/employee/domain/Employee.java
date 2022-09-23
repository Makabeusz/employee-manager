package com.sojka.employeemanager.employee.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String birthDate;
    private String personalId;

}