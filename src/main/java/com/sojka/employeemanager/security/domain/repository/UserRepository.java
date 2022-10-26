package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsername(String username);

    User createNewUser(User user);

    boolean exists(String username);

    void deleteUserByUsername(String username);

    boolean existsByEmail(String email);
}
