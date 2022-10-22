package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.security.domain.User;
import com.sojka.employeemanager.security.domain.UserMapper;
import com.sojka.employeemanager.security.domain.repository.AuthorityRepository;
import com.sojka.employeemanager.security.domain.repository.UserRepository;
import com.sojka.employeemanager.security.dto.RegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MySqlUserDetailsService implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.setAuthorities(authorityRepository.findAuthoritiesByUsername(username));
        return UserMapper.toUserDetails(user);
    }

    @Override
    public RegistrationRequestDto addNewUser(RegistrationRequestDto registrationRequest) {
        return null;
    }
}
