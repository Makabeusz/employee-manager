package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.dto.SampleEmployee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmployeeInMemoryTestDatabase implements SampleEmployee {

    private final Map<Integer, Employee> employees = new HashMap<>();

    public EmployeeInMemoryTestDatabase() {
        employees.put(0, firstEmployee());
        employees.put(1, secondEmployee());
        employees.put(2, thirdEmployee());
    }

    public List<Employee> findAllEmployees() {
        return List.copyOf(employees.values());
    }

    public Optional<Employee> findEmployee(String number) {
        try {
            return Optional.of(employees.get(Integer.parseInt(number)));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

}