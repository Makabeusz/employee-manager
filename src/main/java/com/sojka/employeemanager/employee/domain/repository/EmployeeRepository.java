package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    List<Employee> findAllEmployees();

    Optional<Employee> findEmployee(int number);
}
