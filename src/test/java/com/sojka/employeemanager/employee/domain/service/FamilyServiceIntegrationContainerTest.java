package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.EmployeeManagerApplication;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicatedFamilyException;
import com.sojka.employeemanager.employee.domain.exceptions.NoFamilyException;
import com.sojka.employeemanager.employee.dto.FamilyDto;
import com.sojka.employeemanager.employee.dto.SampleEmployeeFamilyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@Testcontainers
@SpringBootTest(classes = FamilyServiceIntegrationContainerTest.TestConfig.class)
@ActiveProfiles("container")
class FamilyServiceIntegrationContainerTest implements SampleEmployeeFamilyDto {

    @Autowired
    private FamilyService service;

    final String FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD = "1";
    final String SECOND_EMPLOYEE_WITH_WIFE = "2";
    final String THIRD_EMPLOYEE_WITH_NO_FAMILY = "3";

    @Test
    void should_return_all_employee_family() {
        List<FamilyDto> expectedFamily = List.of(firstEmployeeWifeDto(), firstEmployeeChildDto());

        List<FamilyDto> actualFamily = service.getAllFamily(FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD);

        assertThat(actualFamily)
                .containsExactlyInAnyOrderElementsOf(expectedFamily);
    }

    @Test
    void should_return_empty_list_if_employee_has_no_family() {
        List<FamilyDto> actualFamily = service.getAllFamily(THIRD_EMPLOYEE_WITH_NO_FAMILY);

        assertThat(actualFamily).isEmpty();
    }

    @Test
    void should_return_all_employee_children() {
        List<FamilyDto> expectedChildren = List.of(firstEmployeeChildDto());

        List<FamilyDto> actualChildren = service.getAllChildren(FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD);

        assertThat(actualChildren)
                .containsExactlyInAnyOrderElementsOf(expectedChildren);
    }

    @Test
    void should_return_empty_list_if_employee_has_no_children() {
        List<FamilyDto> actualChildren = service.getAllChildren(THIRD_EMPLOYEE_WITH_NO_FAMILY);

        assertThat(actualChildren).isEmpty();
    }

    @Test
    void should_correctly_add_new_employee_family_member() {
        FamilyDto newSecondEmployeeChild = newSecondEmployeeAdultChildDto();

        service.addFamilyMember(newSecondEmployeeChild);

        assertThat(service.getAllChildren(SECOND_EMPLOYEE_WITH_WIFE))
                .contains(newSecondEmployeeAdultChildDto());
    }

    @Test
    void should_throw_DuplicatedFamilyException_for_duplicated_child_adding_attempt() {
        FamilyDto duplicate = firstEmployeeWifeDto();

        Exception exception = catchException(() -> service.addFamilyMember(duplicate));

        assertThat(exception).isInstanceOf(DuplicatedFamilyException.class);
    }

    @Test
    void should_return_all_underage_children_of_the_employee_with_fixed_date_parameter() {
        List<FamilyDto> expectedChildren = List.of(firstEmployeeChildDto());
        String dayBeforeEighteenBirthday = "1998-06-11";

        List<FamilyDto> actualChildren = service.getAllUnderageChildren(FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD
                , dayBeforeEighteenBirthday);

        assertThat(actualChildren)
                .containsExactlyElementsOf(expectedChildren);
    }

    @Test
    void should_return_empty_list_if_all_children_are_adults_with_default_today_date() {
        List<FamilyDto> actualChildren = service.getAllUnderageChildren(FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD
                , new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        assertThat(actualChildren).isEmpty();
    }

    @Test
    void should_add_and_then_remove_family_member() {
        FamilyDto wronglyAdded = wronglyAddedThirdEmployeeChildDto();
        assertThat(service.getAllFamily(THIRD_EMPLOYEE_WITH_NO_FAMILY)).isEmpty();

        service.addFamilyMember(wronglyAdded);
        assertThat(service.getAllFamily(THIRD_EMPLOYEE_WITH_NO_FAMILY)).containsExactly(wronglyAdded);
        service.deleteFamilyMember(wronglyAdded);

        assertThat(service.getAllFamily(THIRD_EMPLOYEE_WITH_NO_FAMILY)).isEmpty();
    }

    @Test
    void should_throw_NoFamilyException_on_deleting_non_existing_family_member_attempt() {
        FamilyDto nonExistingFamilyMember = wronglyAddedThirdEmployeeChildDto();

        Exception exception = catchException(() -> service.deleteFamilyMember(nonExistingFamilyMember));

        assertThat(exception).isInstanceOf(NoFamilyException.class);
    }

    @Import(EmployeeManagerApplication.class)
    static class TestConfig {

    }
}