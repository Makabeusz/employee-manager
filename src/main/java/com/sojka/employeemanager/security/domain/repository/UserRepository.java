package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.UserAccount;

import java.util.Optional;

public interface UserRepository {
    Optional<UserAccount> findUserByUsername(String username);
}
