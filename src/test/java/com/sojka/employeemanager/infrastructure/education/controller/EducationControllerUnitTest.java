package com.sojka.employeemanager.infrastructure.education.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.config.MessageSourceConfig;
import com.sojka.employeemanager.infrastructure.InMemoryTestDatabase;
import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.utils.EducationMapper;
import com.sojka.employeemanager.employee.domain.repository.EducationRepository;
import com.sojka.employeemanager.employee.domain.service.EducationService;
import com.sojka.employeemanager.employee.dto.EducationDto;
import com.sojka.employeemanager.infrastructure.education.dto.SampleEducationDegree;
import com.sojka.employeemanager.infrastructure.education.dto.SampleEducationDegreeDto;
import com.sojka.employeemanager.employee.controller.EducationController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EducationController.class)
@ContextConfiguration(classes = EducationControllerUnitTest.MockMvcConfig.class)
class EducationControllerUnitTest implements SampleEducationDegreeDto, ResultMatcherHelper {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    final String FIRST_EMPLOYEE_ID = "1";

    @Test
    void should_return_all_employee_degrees() throws Exception {
        // given
        List<EducationDto> firstEmployeeDegrees = List.of(firstEmployeeBachelorDegreeDto(),
                firstEmployeeMasterDegreeDto());

        // when
        MvcResult result = mockMvc.perform(get("/employees/" + FIRST_EMPLOYEE_ID + "/education/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(firstEmployeeDegrees);
    }

    @Test
    void should_get_employee_most_recent_degree() throws Exception {
        // given
        String firstEmployeeHighestDegree = "\"degree\":\"Master\"";

        // when
        mockMvc.perform(get("/employees/" + FIRST_EMPLOYEE_ID + "/education/recent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(firstEmployeeHighestDegree)));
    }

    @Test
    void should_return_() {
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

                @Override
                public EducationDto getEmployeeMostRecentDegree(String number) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    Optional<Education> lastDegree = repository.findAllObjects().stream()
                            .filter(education -> education.getId().equals(number))
                            .collect(Collectors.toList()).stream()
                            .min((e1, e2) -> (LocalDate.parse(e2.getFinishDate()).isAfter(LocalDate.parse(e1.getFinishDate()))) ? 1 : 0);
                    return EducationMapper.toEducationDto(
                            lastDegree.orElse(Education.builder().degree("No university degree.").build()));
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