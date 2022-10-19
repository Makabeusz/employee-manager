package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.security.dto.JwtUserDto;
import com.sojka.employeemanager.security.dto.LoginRequestDto;
import com.sojka.employeemanager.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginControllerUnitTest {

    private final JwtUtils jwtUtils = mock(JwtUtils.class);
    private final AuthenticationManager authManager = mock(AuthenticationManager.class);
    private final LoginController loginController = new LoginController(jwtUtils, authManager);

    @Test
    void should_correctly_generate_jwt_token() {
        LoginRequestDto loginRequest = new LoginRequestDto("mockedUser", "mockedPassword");
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final String testToken = "TEST_TOKEN";
        final String expectedResponse = new JwtUserDto(testToken, "mockedUser").toString();

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(testToken);
        ResponseEntity<JwtUserDto> response = loginController.login(loginRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().toString()).isEqualTo(expectedResponse);
    }
}