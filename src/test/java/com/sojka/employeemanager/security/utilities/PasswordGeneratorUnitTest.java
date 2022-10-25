package com.sojka.employeemanager.security.utilities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordGeneratorUnitTest {

    @Test
    void randomly_generated_password_have_at_least_two_characters_of_lowercase_uppercase_digits_and_specials() {
        String password = PasswordGenerator.randomSecurePassword();

        boolean containsAtLeastTwoUppers = password.matches(".*\\p{Upper}.*\\p{Upper}.*");
        boolean containsAtLeastTwoLowers = password.matches(".*\\p{Lower}.*\\p{Lower}.*");
        boolean containsAtLeastTwoSpecials = password.matches(".*\\p{Punct}.*\\p{Punct}.*");
        boolean containsAtLeastTwoDigits = password.matches(".*\\d.*\\d.*");

        assertThat(containsAtLeastTwoUppers).isTrue();
        assertThat(containsAtLeastTwoLowers).isTrue();
        assertThat(containsAtLeastTwoSpecials).isTrue();
        assertThat(containsAtLeastTwoDigits).isTrue();
    }

    @Test
    void randomly_generated_password_is_ten_characters_long() {
        String password = PasswordGenerator.randomSecurePassword();

        assertThat(password.length()).isEqualTo(10);
    }
}