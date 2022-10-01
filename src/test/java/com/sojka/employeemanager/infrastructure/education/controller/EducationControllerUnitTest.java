package com.sojka.employeemanager.infrastructure.education.controller;

import com.sojka.employeemanager.config.MessageSourceConfig;
import com.sojka.employeemanager.infrastructure.InMemoryTestDatabase;
import com.sojka.employeemanager.infrastructure.education.domain.Education;
import com.sojka.employeemanager.infrastructure.education.domain.EducationMapper;
import com.sojka.employeemanager.infrastructure.education.domain.repository.EducationRepository;
import com.sojka.employeemanager.infrastructure.education.domain.service.EducationService;
import com.sojka.employeemanager.infrastructure.education.dto.EducationDto;
import com.sojka.employeemanager.infrastructure.education.dto.SampleEducationDegree;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;

@WebMvcTest(EducationController.class)
@ContextConfiguration(classes = EducationControllerUnitTest.MockMvcConfig.class)
class EducationControllerUnitTest implements SampleEducationDegree {

    private MockMvc mockMvc;

    @Test
    void should_return_all_education_objects() {

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