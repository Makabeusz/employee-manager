package com.sojka.employeemanager.infrastructure.employee.domain.service;

import com.sojka.employeemanager.infrastructure.employee.domain.Employee;
import com.sojka.employeemanager.infrastructure.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.infrastructure.employee.domain.exceptions.DuplicateEmployeeException;
import com.sojka.employeemanager.infrastructure.employee.domain.exceptions.EmployeeNotFoundException;
import com.sojka.employeemanager.infrastructure.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.infrastructure.employee.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }



    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = repository.findAllEmployees();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployee(String number) {
        Employee employee = repository.findEmployeeById(number)
                .orElseThrow(() -> new EmployeeNotFoundException(number));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        try {
            Employee saved = repository.save(employee);
            return EmployeeMapper.mapToEmployeeDto(saved);
        } catch (DuplicateKeyException e) {
            EmployeeDto duplicate = EmployeeMapper.mapToEmployeeDto(
                    repository.findEmployeeByPersonalId(employee.getPersonalId()).get());
            throw new DuplicateEmployeeException(duplicate.toString());
        }
    }

    @Override
    public List<EmployeeDto> addEmployees(List<EmployeeDto> employeeDtos) {
        List<Employee> employees = employeeDtos.stream()
                .map(EmployeeMapper::mapToEmployee)
                .collect(Collectors.toList());
        return repository.saveAll(employees).stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }
}
