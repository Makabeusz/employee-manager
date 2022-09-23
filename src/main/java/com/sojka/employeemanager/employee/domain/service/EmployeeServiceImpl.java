package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeNotFoundException;
import com.sojka.employeemanager.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository; //TODO: Autowire real repository

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = repository.findAllEmployees();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployee(String number) {
        Employee employee = repository.findEmployee(number)
                .orElseThrow(() -> new EmployeeNotFoundException(number));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }
}
