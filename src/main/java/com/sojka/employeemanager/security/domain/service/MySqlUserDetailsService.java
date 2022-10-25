package com.sojka.employeemanager.security.domain.service;

import com.sojka.employeemanager.security.domain.Authority;
import com.sojka.employeemanager.security.domain.User;
import com.sojka.employeemanager.security.domain.UserMapper;
import com.sojka.employeemanager.security.domain.exception.DuplicatedUserException;
import com.sojka.employeemanager.security.domain.repository.AuthorityRepository;
import com.sojka.employeemanager.security.domain.repository.UserRepository;
import com.sojka.employeemanager.security.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MySqlUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.setAuthorities(authorityRepository.findAuthoritiesByUsername(username));
        return UserMapper.toUserDetails(user);
    }

    public UserRegistrationDto addNewUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.exists(userRegistrationDto.getPersonalId())) {
            throw new DuplicatedUserException(userRegistrationDto.toString());
        }
        String password = "randomPass";
        boolean enabled = true;

        User user = User.builder()
                .username(userRegistrationDto.getPersonalId())
                .email(userRegistrationDto.getEmail())
                .password(password)
                .enabled(enabled)
                .build();

        userRepository.createNewUser(user);
        authorityRepository.addUserAuthority(basicUserAuthority(user));

        User saved = userRepository.findUserByUsername(userRegistrationDto.getPersonalId())
                .orElseThrow(() -> new UsernameNotFoundException("User " + userRegistrationDto.getPersonalId() + " have not been created."));
        return UserMapper.toRegistrationDto(saved);
    }

    private Authority basicUserAuthority(User user) {
        return Authority.builder()
                .username(user.getUsername())
                .authority("ROLE_USER")
                .build();
    }
}
