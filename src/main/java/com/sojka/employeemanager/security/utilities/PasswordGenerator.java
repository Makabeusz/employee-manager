package com.sojka.employeemanager.security.utilities;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {



    public static String randomSecurePassword() {
        String upperCaseLetters = RandomStringUtils.random(2,65,90,true,true);
        String lowerCaseLetters = RandomStringUtils.random(2,97,122,true,true);
        String digits = RandomStringUtils.randomNumeric(2);
        String specialChars = RandomStringUtils.random(2,33,47,false,false);
        String totalChars = RandomStringUtils.randomAlphabetic(2);
        StringBuilder combined = new StringBuilder(upperCaseLetters)
                .append(lowerCaseLetters)
                .append(digits)
                .append(specialChars)
                .append(totalChars);
        List<Character> shuffled = combined.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(shuffled);
        return shuffled.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
