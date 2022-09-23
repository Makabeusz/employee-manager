package com.sojka.employeemanager.employee.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private String firstName;
    private String secondName;
    private String lastName;
    private String birthDate;
    private String personalId;

}
