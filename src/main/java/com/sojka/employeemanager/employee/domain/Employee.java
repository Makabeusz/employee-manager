package com.sojka.employeemanager.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String birthDate;
    private String personalId;

}