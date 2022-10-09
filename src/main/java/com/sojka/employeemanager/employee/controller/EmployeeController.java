package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.employee.domain.service.EmployeeService;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService service;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getEmployee(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto employee) {
        return new ResponseEntity<>(service.addEmployee(employee), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<EmployeeDto>> addEmployees(@RequestBody @Valid List<EmployeeDto> employees) {
        return new ResponseEntity<>(service.addEmployees(employees), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") String id) {
        service.deleteEmployee(id);
        String message = "The employee with id " + id + " has been removed.";
        return ResponseEntity.ok(message);
    }

}
