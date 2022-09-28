package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.domain.EmployeeMapper;
import com.sojka.employeemanager.employee.domain.repository.EmployeeRepository;
import com.sojka.employeemanager.employee.dto.EmployeeDto;
import com.sojka.employeemanager.employee.dto.SampleEmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@Testcontainers
@SpringBootTest
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
    void should_throw_exception_for_non_existing_employee() {
        // given
        final String NON_EXISTING_EMPLOYEE = "100";

        // when
        Exception exception = catchException(() -> service.getEmployee(NON_EXISTING_EMPLOYEE));

        // then
        assertThat(exception).isInstanceOf(DataAccessException.class);
    }

    @Test
    void should_save_new_employee_and_then_query_him() {
        // given
        EmployeeDto newEmployee = newEmployeeDto();
        assertThat(repository.findEmployeeByPersonalId(newEmployee.getPersonalId()))
                .isEmpty();

        // when
        EmployeeDto saved = service.addEmployee(newEmployee);
        Optional<Employee> actual = repository.findEmployeeByPersonalId(saved.getPersonalId());

        // then
        assertThat(EmployeeMapper.mapToEmployeeDto(actual.get())).isEqualTo(saved);
    }

    
}