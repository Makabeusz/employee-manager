package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.security.dto.JwtUserDto;
import com.sojka.employeemanager.security.dto.LoginRequestDto;
import com.sojka.employeemanager.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<JwtUserDto> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);
        final JwtUserDto jwtDto = new JwtUserDto(jwt, authentication.getName());
        return ResponseEntity.ok(jwtDto);
    }
}
