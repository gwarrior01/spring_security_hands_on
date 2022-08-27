package com.example.security;

import com.example.enums.EncryptionAlgorithm;
import com.example.security.passwordChecker.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final List<PasswordChecker> passwordCheckers;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        EncryptionAlgorithm encryptionAlgorithm = userDetails.user().getAlgorithm();
        String encodedPassword = userDetails.getPassword();

        boolean passwordMatches = false;
        for (PasswordChecker passwordChecker : passwordCheckers) {
            if (passwordChecker.support(encryptionAlgorithm)) {
                passwordMatches = passwordChecker.checkPassword(rawPassword, encodedPassword);
                break;
            }
        }
        if (passwordMatches) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
