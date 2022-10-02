package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.dto.EducationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    @Override
    public List<EducationDto> getEmployeeDegrees(String number) {
        return null;
    }

    @Override
    public EducationDto getEmployeeMostRecentDegree(String number) {
        return null;
    }
}
