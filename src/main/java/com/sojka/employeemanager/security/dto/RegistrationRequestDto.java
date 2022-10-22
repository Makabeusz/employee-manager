package com.sojka.employeemanager.security.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class RegistrationRequestDto {

    @NotBlank
    private final String personalId;
    @NotBlank
    private final String email;

}