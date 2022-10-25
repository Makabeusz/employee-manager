package com.sojka.employeemanager.security.utilities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorUnitTest {

    @Test
    void randomly_generated_password_have_two_characters_of_lowercase_uppercase_digits_and_special_signs() {
        String password = PasswordGenerator.randomSecurePassword();

        password.matches(".");
    }
}