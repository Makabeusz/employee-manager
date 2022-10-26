package com.sojka.employeemanager.security.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class SecurityErrorResponse {

    private final String message;
    private final HttpStatus status;
}
