package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.EmployeeManagerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = ControllerEndpointsAccessUnitTest.MockMvcConfig.class)
@Testcontainers
@ActiveProfiles("container")
public class ControllerEndpointsAccessUnitTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @ParameterizedTest
    @MethodSource("examplesOfGetEndpoints")
    @WithAnonymousUser
    void anonymous_user_try_get_any_resource_and_result_in_401(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    void anonymous_user_access_login_page_and_result_in_200() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"root\"}"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("examplesOfGetEndpoints")
    @WithUserDetails("USER")
    void employee_basic_user_access_employee_get_resources_and_result_in_200(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("examplesOfPostEndpoints")
    @WithUserDetails("USER")
    void employee_basic_user_try_to_access_post_resources_and_result_in_403(String url) throws Exception {
        mockMvc.perform(post(url))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("examplesOfDeleteEndpoints")
    @WithUserDetails("USER")
    void employee_basic_user_try_to_access_delete_resources_and_result_in_403(String url) throws Exception {
        mockMvc.perform(delete(url))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("examplesOfGetEndpoints")
    @WithUserDetails("ADMIN")
    void admin_user_access_employee_get_resources_and_result_in_200(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("examplesOfPostEndpoints")
    @WithUserDetails("ADMIN")
    void admin_request_post_resources_and_have_access_then_get_400_code_due_to_missing_body(String url) throws Exception {
        mockMvc.perform(post(url))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("examplesOfDeleteEndpoints")
    @WithUserDetails("ADMIN")
    void admin_request_delete_resources_and_have_access_then_get_404_code_due_to_false_body(String url) throws Exception {
        mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":777}"))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> examplesOfGetEndpoints() {
        return Stream.of(arguments("/employees/"),
                arguments("/employees/1"),
                arguments("/employees/1/education"),
                arguments("/employees/1/education/recent"),
                arguments("/employees/1/family"),
                arguments("/employees/1/family/children"));
    }

    private static Stream<Arguments> examplesOfPostEndpoints() {
        return Stream.of(arguments("/employees/"),
                arguments("/employees/list"),
                arguments("/employees/1/education"),
                arguments("/employees/1/family"));
    }

    private static Stream<Arguments> examplesOfDeleteEndpoints() {
        return Stream.of(arguments("/employees/1"),
                arguments("/employees/1/education"),
                arguments("/employees/1/family"));
    }

    @Import(EmployeeManagerApplication.class)
    static class MockMvcConfig {
    }
}
