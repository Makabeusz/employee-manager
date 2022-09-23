package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> findAllEmployees();
}
