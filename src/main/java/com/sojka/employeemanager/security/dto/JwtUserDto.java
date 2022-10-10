package com.sojka.employeemanager.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class JwtUserDto {

    private final String jwt;
    private final String username;
}
