package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.employee.domain.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("container")
class EmployeeControllerIntegrationContainerTest {

    @Autowired
    private EmployeeService service;

    @Test
    void should_return_all_employees() {

        service.getAllEmployees().forEach(System.out::println);
    }
}