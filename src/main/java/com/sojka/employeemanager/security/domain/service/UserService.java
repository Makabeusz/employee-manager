package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.security.dto.RegistrationRequestDto;

public interface UserService {

    RegistrationRequestDto addNewUser(RegistrationRequestDto registrationRequest);
}
