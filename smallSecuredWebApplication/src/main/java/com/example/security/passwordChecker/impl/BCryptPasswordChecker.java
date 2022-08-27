package com.example.security.passwordChecker.impl;

import com.example.enums.EncryptionAlgorithm;
import com.example.security.passwordChecker.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordChecker implements PasswordChecker {

    private final BCryptPasswordEncoder encoder;

    @Override
    public boolean checkPassword(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean support(EncryptionAlgorithm algorithm) {
        return EncryptionAlgorithm.BCRYPT == algorithm;
    }
}
