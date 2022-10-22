package com.sojka.employeemanager.security.domain;

import com.sojka.employeemanager.security.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserMapper {

    static UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }


    static UserRegistrationDto toRegistrationDto(User user) {
        return UserRegistrationDto.builder()
                .personalId(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
