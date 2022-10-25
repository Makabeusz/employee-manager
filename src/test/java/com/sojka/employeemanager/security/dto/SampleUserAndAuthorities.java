package com.sojka.employeemanager.security.dto;

import com.sojka.employeemanager.security.domain.Authority;
import com.sojka.employeemanager.security.domain.User;

public interface SampleUserAndAuthorities {

    default User admin() {
        return User.builder()
                .username("admin")
                .email("john-wick@proton.me")
                .password("$2a$12$uY6DbnZMr8HSAlDvCXvsveOUcExJWRqIDdRCdSO5j3q7omDPcWASK")
                .enabled(true)
                .build();
    }

    default User user() {
        return User.builder()
                .username("user")
                .email("hank_moore3@wp.pl")
                .password("$2a$12$dpMfEKdUkxfDDGs8O6VwMuegRxr1PDK5fZyFWuZT5t/X.5wKqN7oC")
                .enabled(true)
                .build();
    }

    default User newUser() {
        return User.builder()
                .username("newUser")
                .email("new_guy@email.com")
                .password("$2a$12$aufc2abKBOlkEC/rKup3m.LHn.WNtqYhKZVPA.3vZShi9GpOjUX.6")
                .enabled(true)
                .build();
    }

    default Authority adminAuthority() {
        return Authority.builder()
                .username("admin")
                .authority("ROLE_ADMIN")
                .build();
    }

    default Authority userAuthority() {
        return Authority.builder()
                .username("user")
                .authority("ROLE_USER")
                .build();
    }

}