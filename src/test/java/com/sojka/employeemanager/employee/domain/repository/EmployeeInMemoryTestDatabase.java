package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.dto.SampleEmployee;

import java.util.ArrayList;
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
        return Optional.ofNullable(employees.get(Integer.parseInt(number)));
    }

    public Employee saveEmployee(Employee employee) {
        employees.put(employees.size(), employee);
        return employees.get(employees.size());
    }

    public boolean exists(String personalId) {
        return employees.values().stream()
                .anyMatch(employee -> employee.getPersonalId().equals(personalId));
    }

    public List<Employee> saveAllEmployees(List<Employee> employees) {
        List<Employee> saved = new ArrayList<>();
        for (Employee employee : employees) {
            this.employees.put(this.employees.size(), employee);
            saved.add(employee);
        }
        return saved;
    }
}