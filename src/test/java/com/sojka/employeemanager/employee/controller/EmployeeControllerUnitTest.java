package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.EmployeeManagerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = EmployeeControllerUnitTest.MockMvcConfig.class)
class EmployeeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() {
    }

    @Import(EmployeeManagerApplication.class)
    static class MockMvcConfig {

    }
}