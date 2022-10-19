package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.Authority;

import java.util.List;

public interface AuthorityRepository {

    List<Authority> findAuthoritiesByUsername(String username);
}
