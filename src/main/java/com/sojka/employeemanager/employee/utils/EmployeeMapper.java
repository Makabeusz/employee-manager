package com.sojka.employeemanager.employee.utils;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.dto.EmployeeDto;

public interface EmployeeMapper {

    static Employee toEmployee(EmployeeDto employeeDto) {
        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .secondName(employeeDto.getSecondName())
                .lastName(employeeDto.getLastName())
                .birthDate(employeeDto.getBirthDate())
                .personalId(employeeDto.getPersonalId())
                .build();
    }

    static EmployeeDto toEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .secondName(employee.getSecondName())
                .lastName(employee.getLastName())
                .birthDate(employee.getBirthDate())
                .personalId(employee.getPersonalId())
                .build();
    }
}
