package com.sojka.employeemanager.infrastructure.education.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.config.MessageSourceConfig;
import com.sojka.employeemanager.infrastructure.InMemoryTestDatabase;
import com.sojka.employeemanager.infrastructure.education.domain.Education;
import com.sojka.employeemanager.infrastructure.education.domain.EducationMapper;
import com.sojka.employeemanager.infrastructure.education.domain.repository.EducationRepository;
import com.sojka.employeemanager.infrastructure.education.domain.service.EducationService;
import com.sojka.employeemanager.infrastructure.education.dto.EducationDto;
import com.sojka.employeemanager.infrastructure.education.dto.SampleEducationDegree;
import com.sojka.employeemanager.infrastructure.education.dto.SampleEducationDegreeDto;
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

@WebMvcTest(EducationController.class)
@ContextConfiguration(classes = EducationControllerUnitTest.MockMvcConfig.class)

class EducationControllerUnitTest implements SampleEducationDegreeDto {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void should_return_all_employee_degrees() throws Exception {
        // given
        List<EducationDto> firstEmployeeDegrees = List.of(firstEmployeeBachelorDegreeDto(),
                firstEmployeeMasterDegreeDto());
        final String FIRST_EMPLOYEE_ID = "1";

        // when
        MvcResult result = mockMvc.perform(get("/education/" + FIRST_EMPLOYEE_ID))
                .andDo(print())
                .andReturn();


        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(firstEmployeeDegrees);
    }

    private List<EducationDto> listBodyOf(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        ObjectReader reader = mapper.readerForListOf(EducationDto.class);
        return reader.readValue(content);
    }

    @Import(MessageSourceConfig.class)
    static class MockMvcConfig implements SampleEducationDegree {

        private final InMemoryTestDatabase<Education> repository =
                InMemoryTestDatabase.of(firstEmployeeBachelorDegree(), firstEmployeeMasterDegree(), secondEmployeeSecondaryDegree());

        @Bean
        EducationService educationService() {
            return new EducationService() {

                @Override
                public List<EducationDto> getEmployeeDegrees(String number) {
                    return repository.findAllObjects().stream()
                            .filter(education -> education.getId().equals(number))
                            .collect(Collectors.toList()).stream()
                            .map(EducationMapper::toEducationDto)
                            .collect(Collectors.toList());
                }
            };
        }

        @Bean
        EducationController educationController(EducationService service) {
            return new EducationController(service);
        }

        @Bean
        EducationRepository educationRepository() {
            return mock(EducationRepository.class);
        }

    }
}