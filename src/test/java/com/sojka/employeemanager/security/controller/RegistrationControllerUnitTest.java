package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.InMemoryTestDatabase;
import com.sojka.employeemanager.security.config.SecurityConfig;
import com.sojka.employeemanager.security.config.SecurityTestConfigWithMockedRoles;
import com.sojka.employeemanager.security.domain.Authority;
import com.sojka.employeemanager.security.domain.User;
import com.sojka.employeemanager.security.domain.repository.AuthorityRepository;
import com.sojka.employeemanager.security.domain.repository.UserRepository;
import com.sojka.employeemanager.security.domain.service.MySqlUserDetailsService;
import com.sojka.employeemanager.security.dto.SampleUserAndAuthorities;
import com.sojka.employeemanager.security.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@WebMvcTest(RegistrationController.class)
@ContextConfiguration(classes = RegistrationControllerUnitTest.MockMvcConfig.class)
class RegistrationControllerUnitTest {

    @Autowired
    private MySqlUserDetailsService service;


    @Test
    void name() {
        assertThat(service).isNotNull();
    }

    @Import({SecurityConfig.class,
            SecurityTestConfigWithMockedRoles.class})
    static class MockMvcConfig implements SampleUserAndAuthorities {

        private final InMemoryTestDatabase<User> users =
                InMemoryTestDatabase.of(user(), admin());
        private final InMemoryTestDatabase<Authority> authorities =
                InMemoryTestDatabase.of(userAuthority(), adminAuthority());

        @Bean
        MySqlUserDetailsService userDetailsService() {
            return new MySqlUserDetailsService(userRepository(), authorityRepository(), passwordEncoder) {

                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return super.loadUserByUsername(username);
                }

                @Override
                public UserRegistrationDto addNewUser(UserRegistrationDto userRegistrationDto) {
                    return super.addNewUser(userRegistrationDto);
                }
            };
        }

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Bean
        UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        AuthorityRepository authorityRepository() {
            return mock(AuthorityRepository.class);
        }

    }
}