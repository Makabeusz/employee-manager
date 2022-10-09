package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.utils.EmployeeMapper;

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

    default EmployeeDto wrongEmployeeDto() {
        return EmployeeMapper.toEmployeeDto(wrongEmployee());
    }
}