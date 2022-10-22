package com.sojka.employeemanager.security.controller;

import com.sojka.employeemanager.security.dto.RegistrationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @PostMapping
    public ResponseEntity<RegistrationRequestDto> showRegistrationForm() {
        return null;
    }
}
