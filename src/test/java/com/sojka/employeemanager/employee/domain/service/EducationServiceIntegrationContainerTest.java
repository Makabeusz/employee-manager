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
    final String THIRD_EMPLOYEE_WITH_NO_EDUCATION = "3";

    @Test
    void should_return_all_employee_degrees() {
        List<EducationDto> degrees = List.of(firstEmployeeBachelorDegreeDto(), firstEmployeeMasterDegreeDto());

        List<EducationDto> firstEmployeeDegrees = service.getEmployeeDegrees(FIRST_EMPLOYEE_ID);

        assertThat(firstEmployeeDegrees)
                .containsExactlyInAnyOrderElementsOf(degrees);
    }

    @Test
    void should_return_employee_recent_degree() {
        EducationDto mostRecentDegree = firstEmployeeMasterDegreeDto();

        EducationDto employeeMostRecentDegree = service.getEmployeeMostRecentDegree(FIRST_EMPLOYEE_ID);

        assertThat(employeeMostRecentDegree).isEqualTo(mostRecentDegree);
    }

    @Test
    void should_throw_NoEducationException_error_for_query_degrees_of_employee_without_education() {
        Exception exception = catchException(() -> service.getEmployeeMostRecentDegree(THIRD_EMPLOYEE_WITH_NO_EDUCATION));

        assertThat(exception).isInstanceOf(NoEducationException.class);
    }

    @Test
    void should_correctly_add_new_degree_to_existing_employee() {
        EducationDto newDegree = newSecondEmployeeEducationDto();
        assertThat(service.getEmployeeDegrees(SECOND_EMPLOYEE_ID)).doesNotContain(newDegree);

        service.addEmployeeDegree(newDegree);

        assertThat(service.getEmployeeDegrees(SECOND_EMPLOYEE_ID)).contains(newDegree);
    }

    @Test
    void should_throw_DuplicatedEducationException_for_adding_duplicate_attempt() {
        Optional<Education> mostRecentDegree = repository.findMostRecentDegree(FIRST_EMPLOYEE_ID);
        assertThat(mostRecentDegree).isNotEmpty();

        Exception exception = catchException(() ->
                service.addEmployeeDegree(EducationMapper.toEducationDto(mostRecentDegree.get())));

        assertThat(exception).isInstanceOf(DuplicatedEducationException.class);
    }

    @Test
    void should_correctly_add_and_then_remove_employee_degree() {
        EducationDto wronglyAdded = wronglyAddedThirdEmployeeDegreeDto();
        assertThat(service.getEmployeeDegrees(THIRD_EMPLOYEE_WITH_NO_EDUCATION)).isEmpty();

        service.addEmployeeDegree(wronglyAdded);
        assertThat(service.getEmployeeDegrees(THIRD_EMPLOYEE_WITH_NO_EDUCATION)).containsExactly(wronglyAdded);
        service.deleteEmployeeDegree(wronglyAdded);

        assertThat(service.getEmployeeDegrees(THIRD_EMPLOYEE_WITH_NO_EDUCATION)).isEmpty();
    }

    @Test
    void should_throw_NoFamilyException_on_deleting_non_existing_family_member_attempt() {
        EducationDto nonExistingEducation = wronglyAddedThirdEmployeeDegreeDto();

        Exception exception = catchException(() -> service.deleteEmployeeDegree(nonExistingEducation));

        assertThat(exception).isInstanceOf(NoEducationException.class);
    }

    @Import(EmployeeManagerApplication.class)
    static class TestConfig {

    }
}