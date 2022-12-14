package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.EmployeeManagerApplication;
import com.sojka.employeemanager.security.domain.UserMapper;
import com.sojka.employeemanager.security.domain.exception.DuplicatedUserException;
import com.sojka.employeemanager.security.domain.repository.UserRepository;
import com.sojka.employeemanager.security.dto.SampleUserAndAuthorities;
import com.sojka.employeemanager.security.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = RegisterNewUserIntegrationTest.TestConfig.class)
@ActiveProfiles("container")
class RegisterNewUserIntegrationTest implements SampleUserAndAuthorities {

    @Autowired
    private MySqlUserDetailsService service;
    @Autowired
    private UserRepository userRepository;

    @Test
    void should_register_new_user_and_query_for_him() {
        UserRegistrationDto newUser = UserMapper.toRegistrationDto(newUser());
        assertThat(userRepository.exists(newUser.getPersonalId())).isFalse();

        service.addNewUser(newUser);

        assertThat(userRepository.exists(newUser.getPersonalId())).isTrue();
        userRepository.deleteUserByUsername(newUser.getPersonalId());
        assertThat(userRepository.exists(newUser.getPersonalId())).isFalse();
    }

    @Test
    void throw_DuplicatedUserException_for_adding_duplicate_user_attempt() {
        UserRegistrationDto duplicate = UserMapper.toRegistrationDto(user());
        assertThat(userRepository.exists(duplicate.getPersonalId())).isTrue();

        Exception exception = catchException(() -> service.addNewUser(duplicate));

        assertThat(exception).isInstanceOf(DuplicatedUserException.class);
        assertThat(exception).hasMessage("Username user already exits.");
    }

    @Import(EmployeeManagerApplication.class)
    static class TestConfig {
    }
}