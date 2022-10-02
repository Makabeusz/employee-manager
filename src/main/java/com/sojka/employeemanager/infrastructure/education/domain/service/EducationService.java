package com.sojka.employeemanager.infrastructure.education.domain.service;

import com.sojka.employeemanager.infrastructure.education.dto.EducationDto;

import java.util.List;

public interface EducationService {

    List<EducationDto> getEmployeeDegrees(String number);

    EducationDto getEmployeeMostRecentDegree(String number);
}
