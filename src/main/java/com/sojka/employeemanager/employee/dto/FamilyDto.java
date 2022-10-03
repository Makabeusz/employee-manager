package com.sojka.employeemanager.employee.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class FamilyDto {

    @NotBlank(message = "{family.firstname}")
    private String firstName;
    private String secondName;
    @NotBlank(message = "{family.lastname}")
    private String lastName;
    @NotBlank(message = "{family.kinship}")
    private String kinship;
    @NotBlank(message = "{family.birthdate}")
    @Pattern(regexp = "(19|20)\\d{2}-((0[1-9])|(1[0-2]))-(([0-2]\\d)|(30|31))",
            message = "{employee.birthdate}")
    private String birthDate;

}
