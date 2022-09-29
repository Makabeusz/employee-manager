package com.sojka.employeemanager.employee.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class EmployeeDto {

    @NotBlank
    private String firstName;
    private String secondName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Pattern(regexp = "(19|20)\\d{2}-((0[1-9])|(1[0-2]))-(([0-2]\\d)|(30|31))") // yyyy-mm-dd
    private String birthDate;
    @NotBlank
    private String personalId;

}
