package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsername(String username);

    Optional<User> createNewUser(User user);
}
