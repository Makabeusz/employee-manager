package com.sojka.employeemanager.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "{login.username}")
    private final String username;
    @NotBlank(message = "{login.password}")
    private final String password;
}
