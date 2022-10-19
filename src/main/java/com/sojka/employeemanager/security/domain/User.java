package com.sojka.employeemanager.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private String passwordSalt;
    private String passwordHashAlgorithm;
    private List<Authority> authorities;

}
