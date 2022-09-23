package com.sojka.employeemanager.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.repository.EmployeeInMemoryTestDatabase;
import com.sojka.employeemanager.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.employee.domain.service.EmployeeService;
import com.sojka.employeemanager.employee.domain.service.EmployeeServiceImpl;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import com.sojka.employeemanager.employee.dto.SampleEmployee;
import com.sojka.employeemanager.employee.dto.SampleEmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = EmployeeControllerUnitTest.MockMvcConfig.class)
class EmployeeControllerUnitTest implements SampleEmployee, SampleEmployeeDto {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService service;

    @Test
    void should_return_list_of_all_employees() throws Exception {
        // given
        final List<EmployeeDto> expectedEmployees = new ArrayList<>(Arrays.asList(firstEmployeeDto(), secondEmployeeDto(), thirdEmployeeDto()));

        // when
        MvcResult result = mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(expectedEmployees);
    }

    @Test
    void should_return_single_existing_employee() throws Exception {
        // given
        final EmployeeDto expectedEmployee = firstEmployeeDto();
        final int EXISTING_EMPLOYEE = 0;

        // when
        MvcResult result = mockMvc.perform(get("/employees/" + EXISTING_EMPLOYEE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertThat(objectBodyOf(result))
                .isEqualTo(expectedEmployee);
    }


    private List<EmployeeDto> listBodyOf(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        ObjectReader reader = mapper.readerForListOf(EmployeeDto.class);
        return reader.readValue(content);
    }

    private EmployeeDto objectBodyOf(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        return mapper.readValue(content, EmployeeDto.class);
    }


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

                @Override
                public EmployeeDto getEmployee(int number) {
                    return EmployeeMapper.mapToEmployeeDto(
                            repository.findEmployee(number));
                }
            };
        }

        @Bean
        EmployeeController employeeController() {
            return new EmployeeController(employeeService());
        }

        @Bean
        EmployeeRepository employeeRepository() {
            return mock(EmployeeRepository.class);
        }
    }
}