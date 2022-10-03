package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.EmployeeManagerApplication;
import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicatedEducationException;
import com.sojka.employeemanager.employee.domain.exceptions.NoEducationException;
import com.sojka.employeemanager.employee.domain.repository.EducationRepository;
import com.sojka.employeemanager.employee.dto.EducationDto;
import com.sojka.employeemanager.employee.utils.EducationMapper;
import com.sojka.employeemanager.employee.dto.SampleEducationDegreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@SpringBootTest(classes = EducationServiceIntegrationContainerTest.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
class EducationServiceIntegrationContainerTest implements SampleEducationDegreeDto {

    @Autowired
    private EducationService service;
    @Autowired
    private EducationRepository repository;

    final String FIRST_EMPLOYEE_ID = "1";
    final String SECOND_EMPLOYEE_ID = "2";
    final String THIRD_EMPLOYEE_ID = "3";

    @Test
    void should_return_all_employee_degrees() {
        // given
        List<EducationDto> degrees = List.of(firstEmployeeBachelorDegreeDto(), firstEmployeeMasterDegreeDto());


        // when
        List<EducationDto> firstEmployeeDegrees = service.getEmployeeDegrees(FIRST_EMPLOYEE_ID);

        // then
        assertThat(firstEmployeeDegrees)
                .containsExactlyInAnyOrderElementsOf(firstEmployeeDegrees);
    }

    @Test
    void should_return_employee_recent_degree() {
        // given
        EducationDto mostRecentDegree = firstEmployeeMasterDegreeDto();

        // when
        EducationDto employeeMostRecentDegree = service.getEmployeeMostRecentDegree(FIRST_EMPLOYEE_ID);

        // then
        assertThat(employeeMostRecentDegree).isEqualTo(mostRecentDegree);
    }

    @Test
    void should_throw_NoEducationException_error_for_query_degrees_of_employee_without_education() {
        // given
        // when
        Exception exception = catchException(() -> service.getEmployeeMostRecentDegree(THIRD_EMPLOYEE_ID));

        // then
        assertThat(exception).isInstanceOf(NoEducationException.class);
    }

    @Test
    void should_correctly_add_new_degree_to_existing_employee() {
        // given
        EducationDto newDegree = newSecondEmployeeEducationDto();
        assertThat(service.getEmployeeDegrees(SECOND_EMPLOYEE_ID))
                .doesNotContain(newDegree);
        System.out.println(service.getEmployeeDegrees(SECOND_EMPLOYEE_ID));
        // when
        service.addEmployeeDegree(newDegree);

        // then
        assertThat(service.getEmployeeDegrees(SECOND_EMPLOYEE_ID))
                .contains(newDegree);
    }

    @Test
    void should_throw_DuplicatedEducationException_for_adding_duplicate_attempt() {
        // given
        Optional<Education> mostRecentDegree = repository.findMostRecentDegree(FIRST_EMPLOYEE_ID);
        assertThat(mostRecentDegree).isNotEmpty();

        // when
        Exception exception = catchException(() ->
                service.addEmployeeDegree(EducationMapper.toEducationDto(mostRecentDegree.get())));

        // then
        assertThat(exception).isInstanceOf(DuplicatedEducationException.class);
    }

    @Import(EmployeeManagerApplication.class)
    static class TestConfig {

    }
}