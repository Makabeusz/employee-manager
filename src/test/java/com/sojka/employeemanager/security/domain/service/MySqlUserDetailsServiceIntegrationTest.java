package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.EmployeeManagerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles("container")
@SpringBootTest(classes = MySqlUserDetailsServiceIntegrationTest.MySqlUserDetailsTestConfig.class)
class MySqlUserDetailsServiceIntegrationTest {

    @Autowired
    private MySqlUserDetailsService service;

    private final String ADMIN = "ADMIN";

    @Test
    void should_return_existing_user_privileges() {
        final String expectedAuthority = "ROLE_ADMIN";

        UserDetails userDetails = service.loadUserByUsername(ADMIN);
        List<String> actualAuthority = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        assertThat(actualAuthority).containsExactly(expectedAuthority);
    }

    @Import(EmployeeManagerApplication.class)
    static class MySqlUserDetailsTestConfig {
    }
}