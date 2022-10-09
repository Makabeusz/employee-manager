package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.utils.EducationMapper;

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

    default EducationDto newSecondEmployeeEducationDto() {
        return EducationMapper.toEducationDto(newSecondEmployeeBachelorEducation());
    }

    default EducationDto wronglyAddedThirdEmployeeDegreeDto() {
        return EducationMapper.toEducationDto(wronglyAddedThirdEmployeeDegree());
    }

}
