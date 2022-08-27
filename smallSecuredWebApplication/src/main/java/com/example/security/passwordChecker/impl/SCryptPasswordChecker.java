package com.example.security.passwordChecker.impl;

import com.example.enums.EncryptionAlgorithm;
import com.example.security.passwordChecker.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SCryptPasswordChecker implements PasswordChecker {

    private final SCryptPasswordEncoder encoder;

    @Override
    public boolean checkPassword(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean support(EncryptionAlgorithm algorithm) {
        return EncryptionAlgorithm.SCRYPT == algorithm;
    }
}
