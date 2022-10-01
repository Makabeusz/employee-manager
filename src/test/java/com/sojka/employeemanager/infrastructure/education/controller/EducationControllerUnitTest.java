package com.sojka.employeemanager.infrastructure.education.controller;

import com.sojka.employeemanager.config.MessageSourceConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EducationController.class)
@ContextConfiguration(classes = EducationControllerUnitTest.MockMvcConfig.class)
class EducationControllerUnitTest {



    @Import(MessageSourceConfig.class)
    static class MockMvcConfig {

    }
}