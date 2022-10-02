package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.exceptions.NoEducationException;
import com.sojka.employeemanager.employee.domain.repository.EducationRepository;
import com.sojka.employeemanager.employee.dto.EducationDto;
import com.sojka.employeemanager.infrastructure.employee.dto.SampleEducationDegreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@SpringBootTest
@ActiveProfiles("container")
@Testcontainers
class EducationServiceIntegrationContainerTest implements SampleEducationDegreeDto {

    @Autowired
    private EducationService service;
    @Autowired
    private EducationRepository repository;

    final String FIRST_EMPLOYEE_ID = "1";
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
}