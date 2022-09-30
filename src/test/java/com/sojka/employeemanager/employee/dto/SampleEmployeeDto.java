package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.infrastructure.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.infrastructure.employee.dto.EmployeeDto;

import java.util.List;

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

    default EmployeeDto newEmployeeDto() {
        return EmployeeMapper.mapToEmployeeDto(newEmployee());
    }

    default List<EmployeeDto> newEmployeesDto() {
        return List.of(EmployeeMapper.mapToEmployeeDto(john()),
                EmployeeMapper.mapToEmployeeDto(hank()),
                EmployeeMapper.mapToEmployeeDto(luize()));
    }
}