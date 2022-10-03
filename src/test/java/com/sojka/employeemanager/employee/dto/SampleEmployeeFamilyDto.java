package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.utils.FamilyMapper;

public interface SampleEmployeeFamilyDto extends SampleEmployeeFamily {

    default FamilyDto firstEmployeeWifeDto() {
        return FamilyMapper.toFamilyDto(firstEmployeeWife());
    }

    default FamilyDto firstEmployeeChildDto() {
        return FamilyMapper.toFamilyDto(firstEmployeeChild());
    }

    default FamilyDto secondEmployeeWifeDto() {
        return FamilyMapper.toFamilyDto(secondEmployeeWife());
    }

    default FamilyDto newSecondEmployeeChildDto() {
        return FamilyMapper.toFamilyDto(newSecondEmployeeChild());
    }
}
