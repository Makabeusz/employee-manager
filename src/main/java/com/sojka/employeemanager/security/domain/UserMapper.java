package com.sojka.employeemanager.security.domain;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserMapper {

    static UserDetails toUserDetails(UserAccount userAccount) {
        return User.builder()
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .authorities(userAccount.getRole())
                .build();
    }
}
