package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.domain.EmployeeMapper;

public interface SampleEmployeeDto extends SampleEmployee {

    default EmployeeDto firstEmployeeDto() {
        return EmployeeMapper.mapToEmployeeDto(firstEmployee());
    }

    default EmployeeDto secondEmployeeDto() {
        return EmployeeMapper.mapToEmployeeDto(secondEmployee());
    }

    default EmployeeDto thirdEmployeeDto() {
        return EmployeeMapper.mapToEmployeeDto(thirdEmployee());
    }
}