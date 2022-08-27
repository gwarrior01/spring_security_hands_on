package com.example.security.passwordChecker;

import com.example.enums.EncryptionAlgorithm;

public interface PasswordChecker {

    boolean checkPassword(CharSequence rawPassword, String encodedPassword);

    boolean support(EncryptionAlgorithm algorithm);

}
