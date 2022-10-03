package com.sojka.employeemanager.employee.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
public class FamilyDto implements Serializable {

    private static final long serialVersionUID = 1284134747468071096L;

    @NotBlank(message = "{family.id}")
    private String id;
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
