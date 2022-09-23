package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployee(String number);
}
