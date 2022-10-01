package com.sojka.employeemanager.infrastructure.education.dto;

import com.sojka.employeemanager.infrastructure.education.domain.EducationMapper;

public interface SampleEducationDegreeDto extends SampleEducationDegree{

    default EducationDto firstEmployeeBachelorDegreeDto() {
        return EducationMapper.toEducationDto(firstEmployeeBachelorDegree());
    }

    default EducationDto firstEmployeeMasterDegreeDto() {
        return EducationMapper.toEducationDto(firstEmployeeMasterDegree());
    }

    default EducationDto secondEmployeeSecondaryDegreeDto() {
        return EducationMapper.toEducationDto(secondEmployeeSecondaryDegree());
    }

}
