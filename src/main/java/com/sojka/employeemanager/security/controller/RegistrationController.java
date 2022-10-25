package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.security.domain.service.MySqlUserDetailsService;
import com.sojka.employeemanager.security.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private MySqlUserDetailsService service;

    @PostMapping
    public ResponseEntity<UserRegistrationDto> registerAccount(@RequestBody @Valid UserRegistrationDto registrationRequest) {
        return new ResponseEntity<>(service.addNewUser(registrationRequest), HttpStatus.CREATED);
    }

}
