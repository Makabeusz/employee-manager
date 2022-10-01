package com.sojka.employeemanager.infrastructure.education.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class EducationDto {

    @NotBlank(message = "{education.id}")
    private String id;
    @NotBlank(message = "{education.degree}")
    private String degree;
    @NotBlank(message = "{education.school-name}")
    private String schoolName;
    @NotBlank(message = "{education.address}")
    private String address;
    @Pattern(regexp = "(19|20)\\d{2}-((0[1-9])|(1[0-2]))-(([0-2]\\d)|(30|31))",
            message = "{education.start-date}")
    @NotBlank(message = "{education.start-date}")
    private String startDate;
    @Pattern(regexp = "(19|20)\\d{2}-((0[1-9])|(1[0-2]))-(([0-2]\\d)|(30|31))",
            message = "{education.finish-date}")
    @NotBlank(message = "{education.finish-date}")
    private String finishDate;
}
