package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.EmployeeManagerApplication;
import com.sojka.employeemanager.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.repository.EmployeeInMemoryTestDatabase;
import com.sojka.employeemanager.employee.domain.service.EmployeeService;
import com.sojka.employeemanager.employee.domain.service.EmployeeServiceImpl;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = EmployeeControllerUnitTest.MockMvcConfig.class)
class EmployeeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService service;

    @Test
    void should_return_list_of_all_employees() throws Exception {
        mockMvc.perform(get("/employee/getAll"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Import(EmployeeManagerApplication.class)
    static class MockMvcConfig {

        private final EmployeeInMemoryTestDatabase repository = new EmployeeInMemoryTestDatabase();

        @Bean
        EmployeeService employeeService() {
            return new EmployeeServiceImpl() {
                @Override
                public List<EmployeeDto> getAllEmployees() {
                    return repository.findAllEmployees().stream()
                            .map(EmployeeMapper::mapToEmployeeDto)
                            .collect(Collectors.toList());

                }
            };
        }

    }
}