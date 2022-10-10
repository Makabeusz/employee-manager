package com.sojka.employeemanager.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
}
