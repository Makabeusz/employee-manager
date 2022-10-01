package com.sojka.employeemanager.infrastructure.education.controller;

import com.sojka.employeemanager.config.MessageSourceConfig;
import com.sojka.employeemanager.infrastructure.InMemoryTestDatabase;
import com.sojka.employeemanager.infrastructure.education.domain.Education;
import com.sojka.employeemanager.infrastructure.education.domain.repository.EducationRepository;
import com.sojka.employeemanager.infrastructure.education.domain.service.EducationService;
import com.sojka.employeemanager.infrastructure.education.dto.SampleEducationDegree;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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

        private InMemoryTestDatabase<Education> repository =
                InMemoryTestDatabase.of(firstEmployeeBachelorDegree(), firstEmployeeMasterDegree(), secondEmployeeSecondaryDegree());

        @Bean
        EducationService educationService() {
            return new EducationService() {

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