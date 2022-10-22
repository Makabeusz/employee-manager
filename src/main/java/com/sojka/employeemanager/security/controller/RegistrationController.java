package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.security.domain.service.MySqlUserDetailsService;
import com.sojka.employeemanager.security.dto.RegistrationRequestDto;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<RegistrationRequestDto> registerNewAccount(@RequestBody @Valid RegistrationRequestDto registrationRequest) {
        return ResponseEntity.ok(service.addNewUser(registrationRequest));
    }

}
