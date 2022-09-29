package com.sojka.employeemanager.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicateEmployeeException;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeControllerErrorHandler;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeNotFoundException;
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
import org.springframework.http.MediaType;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = EmployeeControllerUnitTest.MockMvcConfig.class)
class EmployeeControllerUnitTest implements SampleEmployee, SampleEmployeeDto, ResultMatcherHelper {

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
                .containsAll(expectedEmployees);
    }

    @Test
    void should_return_single_existing_employee() throws Exception {
        // given
        final EmployeeDto expectedEmployee = firstEmployeeDto();
        final String EXISTING_ID = "0";

        // when
        MvcResult result = mockMvc.perform(get("/employees/" + EXISTING_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertThat(objectBodyOf(result))
                .isEqualTo(expectedEmployee);
    }

    @Test
    void should_throw_EmployeeNotFoundException_for_non_existing_employee() throws Exception {
        // given
        final String NOT_EXISTING_ID = "100";

        // when
        when(service.getEmployee(NOT_EXISTING_ID))
                .thenThrow(new EmployeeNotFoundException(NOT_EXISTING_ID));
        MvcResult result = mockMvc.perform(get("/employees/" + NOT_EXISTING_ID))
                .andDo(print())
                .andExpect(notFoundStatus())
                .andReturn();

        // then
        assertThat(objectBodyOf(result)).describedAs(
                "Employee with id 100 do not exist.");
    }

    @Test
    void should_correctly_save_new_employee() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEmployeeDto())))
                .andDo(print())
                .andExpect(createdStatus())
                .andExpect(containsNewEmployee());
    }

    @Test
    void should_handle_DuplicateEmployeeException_through_handler() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firstEmployeeDto())))
                .andDo(print())
                .andExpect(conflictStatus())
                .andExpect(duplicateEmployeeMessage());
    }

    @Test
    void should_correctly_save_all_employees() throws Exception {
        MvcResult result = mockMvc.perform(post("/employees/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEmployeesDto())))
                .andDo(print())
                .andExpect(createdStatus())
                .andReturn();

        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(newEmployeesDto());
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
            return new EmployeeServiceImpl(employeeRepository()) {
                @Override
                public List<EmployeeDto> getAllEmployees() {
                    return repository.findAllEmployees().stream()
                            .map(EmployeeMapper::mapToEmployeeDto)
                            .collect(Collectors.toList());
                }

                @Override
                public EmployeeDto getEmployee(String number) {
                    Employee employee = repository.findEmployee(number)
                            .orElseThrow(() -> new EmployeeNotFoundException(number));
                    return EmployeeMapper.mapToEmployeeDto(employee);
                }

                @Override
                public EmployeeDto addEmployee(EmployeeDto employeeDto) {
                    if (repository.exists(employeeDto.getPersonalId()))
                        throw new DuplicateEmployeeException(employeeDto.getFirstName());
                    Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
                    return EmployeeMapper.mapToEmployeeDto(
                            repository.saveEmployee(employee));
                }

                @Override
                public List<EmployeeDto> addEmployees(List<EmployeeDto> employeeDtos) {
                    List<Employee> employees = employeeDtos.stream()
                            .map(EmployeeMapper::mapToEmployee)
                            .collect(Collectors.toList());
                    return repository.saveAllEmployees(employees).stream()
                            .map(EmployeeMapper::mapToEmployeeDto)
                            .collect(Collectors.toList());
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

        @Bean
        EmployeeControllerErrorHandler employeeControllerErrorHandler() {
            return new EmployeeControllerErrorHandler();
        }
    }
}