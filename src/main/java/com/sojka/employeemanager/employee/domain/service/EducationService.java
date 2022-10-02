package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.dto.EducationDto;

import java.util.List;

public interface EducationService {

    List<EducationDto> getEmployeeDegrees(String number);

    EducationDto getEmployeeMostRecentDegree(String number);
}
