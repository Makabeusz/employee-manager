package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.employee.domain.service.EmployeeService;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDto>> getAll() {
        List<EmployeeDto> employees = service.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}
