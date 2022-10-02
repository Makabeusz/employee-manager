package com.sojka.employeemanager.infrastructure.employee.dto;

import com.sojka.employeemanager.employee.utils.EducationMapper;
import com.sojka.employeemanager.employee.dto.EducationDto;

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
