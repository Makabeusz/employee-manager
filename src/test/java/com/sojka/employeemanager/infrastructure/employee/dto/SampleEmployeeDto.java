package com.sojka.employeemanager.infrastructure.employee.dto;

import com.sojka.employeemanager.infrastructure.employee.domain.EmployeeMapper;

import java.util.List;

public interface SampleEmployeeDto extends SampleEmployee {

    default EmployeeDto firstEmployeeDto() {
        return EmployeeMapper.toEmployeeDto(firstEmployee());
    }

    default EmployeeDto secondEmployeeDto() {
        return EmployeeMapper.toEmployeeDto(secondEmployee());
    }

    default EmployeeDto thirdEmployeeDto() {
        return EmployeeMapper.toEmployeeDto(thirdEmployee());
    }

    default EmployeeDto newEmployeeDto() {
        return EmployeeMapper.toEmployeeDto(newEmployee());
    }

    default List<EmployeeDto> newEmployeesDto() {
        return List.of(EmployeeMapper.toEmployeeDto(john()),
                EmployeeMapper.toEmployeeDto(hank()),
                EmployeeMapper.toEmployeeDto(luize()));
    }
}