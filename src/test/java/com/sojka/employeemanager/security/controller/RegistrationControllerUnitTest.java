package com.sojka.employeemanager.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sojka.employeemanager.InMemoryTestDatabase;
import com.sojka.employeemanager.ResultMatcherHelper;
import com.sojka.employeemanager.security.config.SecurityConfig;
import com.sojka.employeemanager.security.config.SecurityTestConfigWithMockedRoles;
import com.sojka.employeemanager.security.domain.Authority;
import com.sojka.employeemanager.security.domain.User;
import com.sojka.employeemanager.security.domain.UserMapper;
import com.sojka.employeemanager.security.domain.exception.DuplicatedUserException;
import com.sojka.employeemanager.security.domain.repository.AuthorityRepository;
import com.sojka.employeemanager.security.domain.repository.UserRepository;
import com.sojka.employeemanager.security.domain.service.MySqlUserDetailsService;
import com.sojka.employeemanager.security.dto.SampleUserAndAuthorities;
import com.sojka.employeemanager.security.dto.UserRegistrationDto;
import com.sojka.employeemanager.security.utilities.PasswordGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@ContextConfiguration(classes = RegistrationControllerUnitTest.MockMvcConfig.class)
@Import(RegistrationController.class)
class RegistrationControllerUnitTest implements SampleUserAndAuthorities, ResultMatcherHelper {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void correctly_register_new_user() throws Exception {
        UserRegistrationDto registrationDto = UserMapper.toRegistrationDto(newUser());
        String expectedResponse = "{\"personalId\":\"newUser\",\"email\":\"new_guy@email.com\"}";

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(answerContains(expectedResponse));
    }

    @Test
    void handle_DuplicatedUserException_on_duplicate_register_attempt() throws Exception {
        UserRegistrationDto registrationDto = UserMapper.toRegistrationDto(user());

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationDto)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Import({SecurityConfig.class,
            SecurityTestConfigWithMockedRoles.class})
    static class MockMvcConfig implements SampleUserAndAuthorities {

        @Autowired
        private PasswordEncoder passwordEncoder;

        private final InMemoryTestDatabase<User> userRepo =
                InMemoryTestDatabase.of(user(), admin(), mockedUser());
        private final InMemoryTestDatabase<Authority> authorityRepo =
                InMemoryTestDatabase.of(userAuthority(), adminAuthority(), mockedUserAuthority());

        @Bean
        MySqlUserDetailsService userDetailsService() {
            return new MySqlUserDetailsService(userRepository(), authorityRepository(), passwordEncoder) {

                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    User user = userRepo.findAllObjects().stream()
                            .filter(u -> u.getUsername().equals(username))
                            .findFirst()
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
                    Authority authority = authorityRepo.findAllObjects().stream()
                            .filter(a -> a.getUsername().equals(username))
                            .findFirst()
                            .orElseThrow(() -> new UsernameNotFoundException("Authority connected to username " + username + " does not exist."));
                    user.setAuthorities(List.of(authority));
                    return UserMapper.toUserDetails(user);
                }

                @Override
                public UserRegistrationDto addNewUser(UserRegistrationDto userRegistrationDto) {
                    if (userRepo.exists(userRegistrationDto.getPersonalId()))
                        throw new DuplicatedUserException(userRegistrationDto.getPersonalId());

                    String encryptedPassword = passwordEncoder.encode(
                            PasswordGenerator.randomSecurePassword());
                    boolean enabled = true;

                    User user = User.builder()
                            .username(userRegistrationDto.getPersonalId())
                            .email(userRegistrationDto.getEmail())
                            .password(encryptedPassword)
                            .enabled(enabled)
                            .build();

                    userRepo.saveObject(user);
                    authorityRepo.saveObject(Authority.builder()
                            .username(userRegistrationDto.getPersonalId())
                            .authority("ROLE_USER")
                            .build());

                    User saved = userRepo.findAllObjects().stream()
                            .filter(u -> u.getUsername().equals(userRegistrationDto.getPersonalId()))
                            .findFirst()
                            .orElseThrow(() -> new UsernameNotFoundException("User " + userRegistrationDto.getPersonalId() + " have not been created."));
                    return UserMapper.toRegistrationDto(saved);
                }
            };
        }

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