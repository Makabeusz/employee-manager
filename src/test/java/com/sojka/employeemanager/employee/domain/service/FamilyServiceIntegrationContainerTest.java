package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.EmployeeManagerApplication;
import com.sojka.employeemanager.employee.domain.repository.FamilyRepository;
import com.sojka.employeemanager.employee.dto.FamilyDto;
import com.sojka.employeemanager.employee.dto.SampleEmployeeFamilyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest(classes = FamilyServiceIntegrationContainerTest.TestConfig.class)
@ActiveProfiles("container")
class FamilyServiceIntegrationContainerTest implements SampleEmployeeFamilyDto {

    @Autowired
    private FamilyService service;
    @Autowired
    private FamilyRepository repository;

    final String EMPLOYEE_WITH_WIFE_AND_CHILD = "1";
    final String EMPLOYEE_WITH_WIFE = "2";
    final String EMPLOYEE_WITH_NO_FAMILY = "3";

    @Test
    void should_return_all_employee_family() {
        List<FamilyDto> expectedFamily = List.of(firstEmployeeWifeDto(), firstEmployeeChildDto());

        List<FamilyDto> actualFamily = service.getAllFamily(EMPLOYEE_WITH_WIFE_AND_CHILD);

        assertThat(actualFamily)
                .containsExactlyInAnyOrderElementsOf(actualFamily);
    }

    @Test
    void should_return_empty_list_if_employee_has_no_family() {
        List<FamilyDto> actualFamily = service.getAllFamily(EMPLOYEE_WITH_NO_FAMILY);

        assertThat(actualFamily).isEmpty();
    }

    @Test
    void should_return_all_employee_children() {
        List<FamilyDto> expectedChildren = List.of(firstEmployeeChildDto());

        List<FamilyDto> actualChildren = service.getAllChildren(EMPLOYEE_WITH_WIFE_AND_CHILD);

        assertThat(actualChildren)
                .containsExactlyInAnyOrderElementsOf(expectedChildren);
    }

    @Test
    void should_return_empty_list_if_employee_has_no_children() {
        List<FamilyDto> actualChildren = service.getAllChildren(EMPLOYEE_WITH_NO_FAMILY);

        assertThat(actualChildren).isEmpty();
    }

    @Import(EmployeeManagerApplication.class)
    static class TestConfig {

    }
}