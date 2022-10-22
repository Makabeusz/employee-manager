package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository {

    List<Authority> findAuthoritiesByUsername(String username);

    Optional<Authority> addUserAuthority(Authority authority);
}
