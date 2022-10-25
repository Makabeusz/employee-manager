package com.sojka.employeemanager.security.domain;

import com.sojka.employeemanager.employee.domain.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority, DomainObject {

    private static final long serialVersionUID = 9145676999319422711L;

    private String username;
    private String authority;

    @Override
    public String getObjectId() {
        return username + " " + authority;
    }
}


