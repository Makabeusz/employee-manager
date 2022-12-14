package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.EmployeeManagerApplication;
import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeNotFoundException;
import com.sojka.employeemanager.employee.utils.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicateEmployeeException;
import com.sojka.employeemanager.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import com.sojka.employeemanager.employee.dto.SampleEmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.api.Assertions.catchThrowable;

@Testcontainers
@SpringBootTest(classes = EmployeeServiceIntegrationContainerTest.TestConfig.class)
@ActiveProfiles("container")
class EmployeeServiceIntegrationContainerTest implements SampleEmployeeDto {

    @Autowired
    private EmployeeService service;
    @Autowired
    private EmployeeRepository repository;

    @Test
    void should_return_all_employees() {
        // given
        List<EmployeeDto> expectedEmployees = List.of(firstEmployeeDto(), secondEmployeeDto(), thirdEmployeeDto());

        // when
        List<EmployeeDto> actualEmployees = service.getAllEmployees();

        // then
        assertThat(actualEmployees)
                .containsExactlyInAnyOrderElementsOf(expectedEmployees);
    }

    @Test
    void should_return_existing_employee_by_its_id() {
        // given
        EmployeeDto expectedEmployee = firstEmployeeDto();
        final String FIRST_EMPLOYEE_NUMBER = "1";

        // when
        EmployeeDto actualEmployee = service.getEmployee(FIRST_EMPLOYEE_NUMBER);

        // then
        assertThat(actualEmployee).isEqualTo(expectedEmployee);
    }

    @Test
    void should_throw_EmployeeNotFoundException_for_non_existing_employee() {
        // given
        final String NON_EXISTING_EMPLOYEE = "100";

        // when
        Exception exception = catchException(() -> service.getEmployee(NON_EXISTING_EMPLOYEE));

        // then
        assertThat(exception)
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee with id 100 do not exist.");
    }

    @Test
    void should_save_new_employee_and_then_query_him() {
        // given
        EmployeeDto newEmployee = newEmployeeDto();
        assertThat(repository.findEmployeeByPersonalId(newEmployee.getPersonalId()))
                .isEmpty();

        // when
        EmployeeDto saved = service.addEmployee(newEmployee);
        Employee actual = repository.findEmployeeByPersonalId(saved.getPersonalId())
                .orElseThrow();

        // then
        assertThat(EmployeeMapper.toEmployeeDto(actual)).isEqualTo(saved);
    }

    @Test
    void should_throw_DuplicateEmployeeException_for_duplicate_saving_attempt() {
        // given
        EmployeeDto existing = firstEmployeeDto();
        String existingEmployeePersonalId = existing.getPersonalId();

        // when / then
        assertThatThrownBy(() -> service.addEmployee(existing))
                .hasMessageContaining(existingEmployeePersonalId)
                .isInstanceOf(DuplicateEmployeeException.class);
    }

    @Test
    void should_throw_DuplicateEmployeeException_and_refused_to_save_any_employees_if_duplicate_is_within() {
        // given
        List<EmployeeDto> primaryList = repository.findAllEmployees().stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
        List<EmployeeDto> newWithOneDuplicate = new ArrayList<>(List.of(thirdEmployeeDto()));
        newWithOneDuplicate.addAll(newEmployeesDto());

        // when
        Throwable exception = catchThrowable(() -> service.addEmployees(newWithOneDuplicate));

        // then
        assertThat(exception).isInstanceOf(DuplicateKeyException.class);
        assertThat(service.getAllEmployees())
                .containsExactlyInAnyOrderElementsOf(primaryList);
    }

    @Test
    void should_correctly_save_employees_and_then_query_for_them() {
        // given
        List<EmployeeDto> primaryList = repository.findAllEmployees().stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
        List<EmployeeDto> newEmployees = new ArrayList<>(newEmployeesDto());

        // when
        List<EmployeeDto> saved = service.addEmployees(newEmployees);

        // then
        assertThat(service.getAllEmployees())
                .containsAll(primaryList)
                .containsAll(saved);
    }

    @Test
    void should_correctly_add_and_then_remove_employee() {
        EmployeeDto wronglyAdded = wrongEmployeeDto();
        assertThat(service.getAllEmployees()).doesNotContain(wronglyAdded);

        service.addEmployee(wronglyAdded);
        assertThat(service.getAllEmployees()).contains(wronglyAdded);
        service.deleteEmployee(wronglyAdded.getPersonalId());

        assertThat(service.getAllEmployees()).doesNotContain(wronglyAdded);
    }

    @Test
    void should_throw_EmployeeNotFoundException_on_deleting_non_existing_employee_attempt() {
        String nonExistingEmployeeId = wrongEmployeeDto().getPersonalId();

        Exception exception = catchException(() -> service.deleteEmployee(nonExistingEmployeeId));

        assertThat(exception).isInstanceOf(EmployeeNotFoundException.class);
    }

    @Import(EmployeeManagerApplication.class)
    static class TestConfig {

    }
}