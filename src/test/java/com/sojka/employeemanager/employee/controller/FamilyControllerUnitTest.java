package com.sojka.employeemanager.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.InMemoryTestDatabase;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.employee.domain.Family;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicatedFamilyException;
import com.sojka.employeemanager.employee.domain.exceptions.handler.FamilyControllerErrorHandler;
import com.sojka.employeemanager.employee.domain.repository.FamilyRepository;
import com.sojka.employeemanager.employee.domain.service.FamilyService;
import com.sojka.employeemanager.employee.dto.FamilyDto;
import com.sojka.employeemanager.employee.dto.SampleEmployeeFamily;
import com.sojka.employeemanager.employee.dto.SampleEmployeeFamilyDto;
import com.sojka.employeemanager.employee.utils.FamilyMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FamilyController.class)
@ContextConfiguration(classes = FamilyControllerUnitTest.MockMvcConfig.class)
class FamilyControllerUnitTest implements SampleEmployeeFamilyDto, ResultMatcherHelper {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    final String FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD = "1";
    final String SECOND_EMPLOYEE_WITH_WIFE_AND_NEWBORN = "2";
    final String THIRD_EMPLOYEE_WITH_NO_FAMILY = "3";

    @Test
    void should_return_all_employee_family_members() throws Exception {
        List<FamilyDto> actualFamily = List.of(firstEmployeeWifeDto(), firstEmployeeChildDto());

        MvcResult result = mockMvc.perform(get("/employee/" + FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD + "/family"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(actualFamily);
    }

    @Test
    void should_return_empty_list_if_employee_has_no_family() throws Exception {
        String emptyList = "[]";

        mockMvc.perform(get("/employee/" + THIRD_EMPLOYEE_WITH_NO_FAMILY + "/family"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(answerContains(emptyList));
    }

    @Test
    void should_return_all_employee_children() throws Exception {
        List<FamilyDto> actualChildren = List.of(firstEmployeeChildDto());

        MvcResult result = mockMvc.perform(get("/employee/" + FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD + "/family/children"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(actualChildren);
    }

    @Test
    void should_return_empty_list_if_employee_has_no_children() throws Exception {
        String emptyList = "[]";

        mockMvc.perform(get("/employee/" + THIRD_EMPLOYEE_WITH_NO_FAMILY + "/family/children"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(answerContains(emptyList));
    }

    @Test
    void should_correctly_add_new_family_member() throws Exception {
        String newChild = mapper.writeValueAsString(newSecondEmployeeAdultChildDto());

        mockMvc.perform(post("/employee/" + SECOND_EMPLOYEE_WITH_WIFE_AND_NEWBORN + "/family")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newChild))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(answerContains(newChild));
    }

    @Test
    void should_throw_DuplicatedFamilyException_on_adding_duplicate_family_member_attempt() throws Exception {
        String duplicate = mapper.writeValueAsString(firstEmployeeChildDto());
        String duplicateMessage = "The employee family member already exists";

        mockMvc.perform(post("/employee/" + SECOND_EMPLOYEE_WITH_WIFE_AND_NEWBORN + "/family")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicate))
                .andDo(print())
                .andExpect(conflictStatus())
                .andExpect(answerContains(duplicateMessage));
    }

    @Test
    void should_return_only_all_underage_children_if_they_exists() throws Exception {
        String newbornChildOnly = "[" + mapper.writeValueAsString(secondEmployeeNewbornChildDto()) + "]";

        mockMvc.perform(get("/employee/" + SECOND_EMPLOYEE_WITH_WIFE_AND_NEWBORN + "/family/underage-children"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(newbornChildOnly));
    }

    @Test
    void should_return_empty_list_if_no_underage_children_exists() throws Exception {
        String emptyList = "[]";

        mockMvc.perform(get("/employee/" + FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD + "/family/underage-children"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(emptyList));
    }

    @Test
    void should_return_all_underage_children_up_to_given_date_parameter() throws Exception {
        String dayBeforeEighteenBirthday = "1998-06-11";
        String firstEmployeeChildOnly = "[" + mapper.writeValueAsString(firstEmployeeChildDto()) + "]";

        mockMvc.perform(get("/employee/" + FIRST_EMPLOYEE_WITH_WIFE_AND_ADULT_CHILD +
                        "/family/underage-children/?beforeDate={date}", dayBeforeEighteenBirthday))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(firstEmployeeChildOnly));
    }

    private List<FamilyDto> listBodyOf(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        ObjectReader reader = mapper.readerForListOf(FamilyDto.class);
        return reader.readValue(content);
    }

    @Import(FamilyControllerErrorHandler.class)
    static class MockMvcConfig implements SampleEmployeeFamily {

        private final InMemoryTestDatabase<Family> repository =
                InMemoryTestDatabase.of(firstEmployeeWife(), firstEmployeeChild(), secondEmployeeWife(), secondEmployeeNewbornChild());

        @Bean
        FamilyService familyService() {
            return new FamilyService() {
                @Override
                public List<FamilyDto> getAllFamily(String id) {
                    return repository.findAllObjects().stream()
                            .filter(member -> member.getObjectId().startsWith(id))
                            .map(FamilyMapper::toFamilyDto)
                            .collect(Collectors.toList());
                }

                @Override
                public List<FamilyDto> getAllChildren(String id) {
                    return repository.findAllObjects().stream()
                            .filter(member -> member.getObjectId().startsWith(id))
                            .filter(member -> member.getKinship().equals("child"))
                            .map(FamilyMapper::toFamilyDto)
                            .collect(Collectors.toList());
                }

                @Override
                public FamilyDto addFamilyMember(FamilyDto familyDto) {
                    Family family = FamilyMapper.toFamily(familyDto);
                    if (repository.exists(family.getObjectId())) throw new DuplicatedFamilyException(family.toString());
                    return FamilyMapper.toFamilyDto(
                            repository.saveObject(family));
                }

                @Override
                public List<FamilyDto> getAllUnderageChildren(String id, String date) {
                    return getAllChildren(id).stream()
                            .filter(child -> LocalDate.parse(child.getBirthDate()).plus(18, ChronoUnit.YEARS).isEqual(LocalDate.parse(date)) ||
                                    LocalDate.parse(child.getBirthDate()).plus(18, ChronoUnit.YEARS).isAfter(LocalDate.parse(date)))
                            .collect(Collectors.toList());
                }
            };
        }

        @Bean
        FamilyController familyController() {
            return new FamilyController(familyService());
        }

        @Bean
        FamilyRepository familyRepository() {
            return mock(FamilyRepository.class);
        }
    }
}