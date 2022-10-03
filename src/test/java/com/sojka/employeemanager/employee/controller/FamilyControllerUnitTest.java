package com.sojka.employeemanager.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.InMemoryTestDatabase;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.employee.domain.Family;
import com.sojka.employeemanager.employee.domain.exceptions.NoFamilyException;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FamilyController.class)
@ContextConfiguration(classes = FamilyControllerUnitTest.MockMvcConfig.class)
class FamilyControllerUnitTest implements SampleEmployeeFamilyDto, ResultMatcherHelper {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    final String FIRST_EMPLOYEE_ID = "1";
    final String EMPLOYEE_WITH_NO_FAMILY_ID = "3";

    @Test
    void should_return_all_employee_family_members() throws Exception {
        List<FamilyDto> actualFamily = List.of(firstEmployeeWifeDto(), firstEmployeeChildDto());

        MvcResult result = mockMvc.perform(get("/employee/" + FIRST_EMPLOYEE_ID + "/family"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(actualFamily);
    }

    @Test
    void should_return_no_content_status_if_employee_has_no_family() throws Exception {
        mockMvc.perform(get("/employee/" + EMPLOYEE_WITH_NO_FAMILY_ID + "/family"))
                .andDo(print())
                .andExpect(noContentStatus())
                .andExpect(answerContains("The employee with ID 3 have no family."));
    }

    private List<FamilyDto> listBodyOf(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        ObjectReader reader = mapper.readerForListOf(FamilyDto.class);
        return reader.readValue(content);
    }

    @Import(FamilyControllerErrorHandler.class)
    static class MockMvcConfig implements SampleEmployeeFamily {

        private final InMemoryTestDatabase<Family> repository =
                InMemoryTestDatabase.of(firstEmployeeWife(), firstEmployeeChild(), secondEmployeeWife());

        @Bean
        FamilyService familyService() {
            return new FamilyService() {
                @Override
                public List<FamilyDto> getAllFamilyMembers(String id) {
                    List<FamilyDto> familyMembers = repository.findAllObjects().stream()
                            .filter(member -> member.getId().equals(id))
                            .map(FamilyMapper::toFamilyDto)
                            .collect(Collectors.toList());
                    if (familyMembers.isEmpty()) throw new NoFamilyException(id);
                    return familyMembers;
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