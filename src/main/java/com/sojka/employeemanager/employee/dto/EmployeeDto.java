package com.sojka.employeemanager.employee.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = 5614919473534210281L;

    @NotBlank(message = "{employee.firstname}")
    private String firstName;
    private String secondName;
    @NotBlank(message = "{employee.lastname}")
    private String lastName;
    @NotBlank(message = "{employee.birthdate}")
    @Pattern(regexp = "(19|20)\\d{2}-((0[1-9])|(1[0-2]))-(([0-2]\\d)|(30|31))",
            message = "{employee.birthdate}")
    private String birthDate;
    @NotBlank(message = "{employee.personalid}")
    private String personalId;

}
