package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.security.domain.UserAccount;
import com.sojka.employeemanager.security.domain.UserMapper;
import com.sojka.employeemanager.security.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MySqlUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = repository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return UserMapper.toUserDetails(user);
    }
}
