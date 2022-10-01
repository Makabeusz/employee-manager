package com.sojka.employeemanager.infrastructure.education.controller;

import com.sojka.employeemanager.config.MessageSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EducationController.class)
@ContextConfiguration(classes = EducationControllerUnitTest.MockMvcConfig.class)
class EducationControllerUnitTest {

    private MockMvc mockMvc;

    @Test
    void should_return_all_education_objects() {

    }

    @Import(MessageSourceConfig.class)
    static class MockMvcConfig {

//        @Bean

    }
}