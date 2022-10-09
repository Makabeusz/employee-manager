package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.utils.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicateEmployeeException;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeNotFoundException;
import com.sojka.employeemanager.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = repository.findAllEmployees();
        return employees.stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployee(String number) {
        Employee employee = repository.findEmployeeById(number)
                .orElseThrow(() -> new EmployeeNotFoundException(number));
        return EmployeeMapper.toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.toEmployee(employeeDto);
        try {
            Employee saved = repository.save(employee);
            return EmployeeMapper.toEmployeeDto(saved);
        } catch (DuplicateKeyException e) {
            EmployeeDto duplicate = EmployeeMapper.toEmployeeDto(
                    repository.findEmployeeByPersonalId(employee.getPersonalId()).get());
            throw new DuplicateEmployeeException(duplicate.toString());
        }
    }

    @Override
    public List<EmployeeDto> addEmployees(List<EmployeeDto> employeeDtos) {
        List<Employee> employees = employeeDtos.stream()
                .map(EmployeeMapper::toEmployee)
                .collect(Collectors.toList());
        return repository.saveAll(employees).stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(String id) {
        Employee employee = repository.findEmployeeByPersonalId(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        repository.delete(employee.getPersonalId());
    }
}
