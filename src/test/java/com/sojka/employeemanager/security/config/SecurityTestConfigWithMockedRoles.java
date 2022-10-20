package com.sojka.employeemanager.security.config;

import com.sojka.employeemanager.security.jwt.AuthTokenFilter;
import com.sojka.employeemanager.security.jwt.JwtUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class SecurityTestConfigWithMockedRoles {

    @Bean
    @Primary
    AuthenticationEntryPoint authenticationEntryPoint() {
        return mock(AuthenticationEntryPoint.class);
    }

    @Bean
    @Primary
    JwtUtils jwtUtils() {
        final JwtUtils mockUtils = mock(JwtUtils.class);
        when(mockUtils.validateJwtToken(any())).thenReturn(true);
        when(mockUtils.getUsernameFromJwtToken(any())).thenReturn("mockedUser");
        when(mockUtils.parseJwt(any())).thenReturn("mockedToken");
        return mockUtils;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> new User("mockedUser", "mockedPassword", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Bean
    @Primary
    AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(jwtUtils(), userDetailsService());
    }

    @Bean
    DataSource dataSource() {
        return mock(DataSource.class);
    }

}