package com.sojka.employeemanager.security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@RequiredArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "{registration.personal-id}")
    private final String personalId;
    @NotBlank(message = "{registration.email}")
    private final String email;

}