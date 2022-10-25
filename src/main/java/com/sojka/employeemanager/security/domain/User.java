package com.sojka.employeemanager.security.domain;

import com.sojka.employeemanager.employee.domain.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements DomainObject {

    private String username;
    private String email;
    private String password;
    private List<Authority> authorities;
    private boolean enabled;

    @Override
    public String getObjectId() {
        return username;
    }
}
