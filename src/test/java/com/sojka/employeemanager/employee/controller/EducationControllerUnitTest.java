package com.sojka.employeemanager.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.config.MessageSourceConfig;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicatedEducationException;
import com.sojka.employeemanager.employee.domain.exceptions.EducationControllerErrorHandler;
import com.sojka.employeemanager.employee.domain.exceptions.NoEducationException;
import com.sojka.employeemanager.InMemoryTestDatabase;
import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.utils.EducationMapper;
import com.sojka.employeemanager.employee.domain.repository.EducationRepository;
import com.sojka.employeemanager.employee.domain.service.EducationService;
import com.sojka.employeemanager.employee.dto.EducationDto;
import com.sojka.employeemanager.employee.dto.SampleEducationDegree;
import com.sojka.employeemanager.employee.dto.SampleEducationDegreeDto;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EducationController.class)
@ContextConfiguration(classes = EducationControllerUnitTest.MockMvcConfig.class)
class EducationControllerUnitTest implements SampleEducationDegreeDto, ResultMatcherHelper {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    final String FIRST_EMPLOYEE_ID = "1";
    final String EMPLOYEE_WITHOUT_DEGREE = "3";

    @Test
    void should_return_all_employee_degrees() throws Exception {
        List<EducationDto> firstEmployeeDegrees = List.of(firstEmployeeBachelorDegreeDto(),
                firstEmployeeMasterDegreeDto());

        MvcResult result = mockMvc.perform(get("/employees/" + FIRST_EMPLOYEE_ID + "/education/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(listBodyOf(result))
                .containsExactlyInAnyOrderElementsOf(firstEmployeeDegrees);
    }

    @Test
    void should_get_employee_most_recent_degree() throws Exception {
        String firstEmployeeHighestDegree = "\"degree\":\"Master\"";

        mockMvc.perform(get("/employees/" + FIRST_EMPLOYEE_ID + "/education/recent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(answerContains(firstEmployeeHighestDegree));
    }

    @Test
    void should_return_204_for_employee_without_university_degree() throws Exception {
        mockMvc.perform(get("/employees/" + EMPLOYEE_WITHOUT_DEGREE + "/education/recent"))
                .andDo(print())
                .andExpect(noContentStatus());
    }

    @Test
    void should_correctly_save_new_degree_for_employee() throws Exception {
        String bachelorDegree = "\"degree\":\"Bachelor\"";

        mockMvc.perform(post("/employees/" + EMPLOYEE_WITHOUT_DEGREE + "/education")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bachelorDegree()))
                .andDo(print())
                .andExpect(createdStatus())
                .andExpect(answerContains(bachelorDegree));
    }

    @Test
    void should_throw_DuplicatedEducationException_for_add_duplicate_degree_attempt() throws Exception {
        String duplicatedDegree = mapper.writeValueAsString(firstEmployeeMasterDegree());

        mockMvc.perform(post("/employees/" + FIRST_EMPLOYEE_ID + "/education")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicatedDegree))
                .andDo(print())
                .andExpect(conflictStatus())
                .andExpect(answerContains("The employee already have such degree"));
    }

    private String bachelorDegree() throws JsonProcessingException {
        return mapper.writeValueAsString(newSecondEmployeeBachelorEducation());
    }

    private List<EducationDto> listBodyOf(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        ObjectReader reader = mapper.readerForListOf(EducationDto.class);
        return reader.readValue(content);
    }

    @Import({MessageSourceConfig.class, EducationControllerErrorHandler.class})
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
                    Optional<Education> lastDegree = repository.findAllObjects().stream()
                            .filter(education -> education.getId().equals(number))
                            .collect(Collectors.toList()).stream()
                            .min((e1, e2) -> (LocalDate.parse(e2.getFinishDate()).isAfter(LocalDate.parse(e1.getFinishDate()))) ? 1 : 0);
                    return EducationMapper.toEducationDto(
                            lastDegree.orElseThrow(() -> new NoEducationException(number)));
                }

                @Override
                public EducationDto addEmployeeDegree(EducationDto educationDto) {
                    Education education = EducationMapper.toEducation(educationDto);
                    List<Education> allObjects = repository.findAllObjects();
                    for (Education object : allObjects) {
                        if (object.equals(education)) throw new DuplicatedEducationException(object.toString());
                    }
                    return EducationMapper.toEducationDto(
                            repository.saveObject(education));
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