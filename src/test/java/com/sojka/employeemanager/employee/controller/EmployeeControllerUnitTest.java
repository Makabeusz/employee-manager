package com.sojka.employeemanager.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.config.MessageSourceConfig;
import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.dto.EducationDto;
import com.sojka.employeemanager.employee.utils.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.exceptions.handler.EmployeeControllerErrorHandler;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeNotFoundException;
import com.sojka.employeemanager.InMemoryTestDatabase;
import com.sojka.employeemanager.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.employee.domain.service.EmployeeService;
import com.sojka.employeemanager.employee.domain.service.EmployeeServiceImpl;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import com.sojka.employeemanager.employee.dto.SampleEmployee;
import com.sojka.employeemanager.employee.dto.SampleEmployeeDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    void should_handle_DuplicateEmployeeException_on_duplicate_saving_attempt() throws Exception {
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

    @Test
    void should_throw_DuplicateEmployeeException_during_save_attempt_of_list_containing_a_duplicate() throws Exception {
        mockMvc.perform(post("/employees/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeesAndOneDuplicate()))
                .andDo(print())
                .andExpect(conflictStatus())
                .andExpect(duplicateEmployeeMessage());
    }

    @ParameterizedTest
    @MethodSource("malformedEmployees")
    void employeeDto_validation_check(String argument, String validationMessage) throws Exception {
        // given
        String malformedEmployee = argument;

        // when
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedEmployee))
                .andDo(print())
                .andExpect(badRequestStatus())
                .andExpect(content().string(Matchers.containsString(validationMessage)));
    }

    @Test
    void should_correctly_remove_employee_existing_degree() throws Exception {
        String wronglyAddedEmployee = mapper.writeValueAsString(wrongEmployeeDto());
        String wronglyAddedEmployeeId = "5";
        service.addEmployee(wrongEmployeeDto());

        mockMvc.perform(delete("/employees/" + wronglyAddedEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wronglyAddedEmployee))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(answerContains("The employee with id 5 has been removed."));
    }

    @Test
    void should_throw_EmployeeNotFoundException_for_deleting_non_existing_degree_attempt() throws Exception {
        String nonExistingEmployeeJson = mapper.writeValueAsString(wrongEmployeeDto());
        String wronglyAddedEmployeeId = "5";

        mockMvc.perform(delete("/employees/" + wronglyAddedEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nonExistingEmployeeJson))
                .andDo(print())
                .andExpect(notFoundStatus())
                .andExpect(answerContains("Employee with id 5 do not exist."));
    }

    private static Stream<Arguments> malformedEmployees() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee wellConstructedEmployee = Employee.builder()
                .firstName("name")
                .secondName("second name")
                .lastName("lastname")
                .birthDate("1995-06-20")
                .personalId("12345612345")
                .build();
        EmployeeDto wrongDate = EmployeeMapper.toEmployeeDto(wellConstructedEmployee);
        EmployeeDto noName = EmployeeMapper.toEmployeeDto(wellConstructedEmployee);
        EmployeeDto noSurname = EmployeeMapper.toEmployeeDto(wellConstructedEmployee);
        EmployeeDto noPersonalId = EmployeeMapper.toEmployeeDto(wellConstructedEmployee);
        wrongDate.setBirthDate("2000-13-23");
        noName.setFirstName("");
        noSurname.setLastName("");
        noPersonalId.setPersonalId("");
        return Stream.of(arguments(mapper.writeValueAsString(wrongDate), "Employee birthdate should be given in correct format (yyyy-mm-dd)."),
                arguments(mapper.writeValueAsString(noName), "Employee firstname is required."),
                arguments(mapper.writeValueAsString(noSurname), "Employee lastname is required."),
                arguments(mapper.writeValueAsString(noPersonalId), "Employee personal id is required."));
    }

    private String newEmployeesAndOneDuplicate() throws JsonProcessingException {
        return mapper.writeValueAsString(List.of(john(), hank(), firstEmployee()));
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

    @Import(MessageSourceConfig.class)
    static class MockMvcConfig implements SampleEmployee {

        private final InMemoryTestDatabase<Employee> repository = InMemoryTestDatabase.of(firstEmployee(), secondEmployee(), thirdEmployee());

        @Bean
        EmployeeService employeeService() {
            return new EmployeeServiceImpl(employeeRepository()) {
                @Override
                public List<EmployeeDto> getAllEmployees() {
                    return repository.findAllObjects().stream()
                            .map(EmployeeMapper::toEmployeeDto)
                            .collect(Collectors.toList());
                }

                @Override
                public EmployeeDto getEmployee(String number) {
                    Employee employee = repository.findObject(number)
                            .orElseThrow(() -> new EmployeeNotFoundException(number));
                    return EmployeeMapper.toEmployeeDto(employee);
                }

                @Override
                public EmployeeDto addEmployee(EmployeeDto employeeDto) {
                    if (repository.exists(employeeDto.getPersonalId()))
                        throw new DuplicateKeyException(employeeDto.toString());
                    Employee employee = EmployeeMapper.toEmployee(employeeDto);
                    return EmployeeMapper.toEmployeeDto(
                            repository.saveObject(employee));
                }

                @Override
                public List<EmployeeDto> addEmployees(List<EmployeeDto> employeeDtos) {
                    List<Employee> employees = employeeDtos.stream()
                            .map(EmployeeMapper::toEmployee)
                            .collect(Collectors.toList());
                    return repository.saveAllObjects(employees).stream()
                            .map(EmployeeMapper::toEmployeeDto)
                            .collect(Collectors.toList());
                }

                @Override
                public void deleteEmployee(String id) {
                    Employee employee = repository.findObject(id).orElseThrow(() -> new EmployeeNotFoundException(id));
                    repository.remove(employee.getObjectId());
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