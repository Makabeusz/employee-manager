package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.security.dto.UserRegistrationDto;

public interface UserService {

    UserRegistrationDto addNewUser(UserRegistrationDto registrationRequest);
}
