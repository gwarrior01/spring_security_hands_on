package com.example.service;

import com.example.entity.Otp;
import com.example.entity.User;
import com.example.repository.OtpRepository;
import com.example.repository.UserRepository;
import com.example.util.GenerateCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User user) {
        Optional<User> opUser = userRepository.findUserByUsername(user.getUsername());
        if (opUser.isPresent()) {
            User userFromDB = opUser.get();
            if (passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())) {
                renewOtp(userFromDB);
            } else {
                throw new BadCredentialsException("Bad credential...");
            }
        } else {
            throw new BadCredentialsException("Bad credential...");
        }
    }

    private void renewOtp(User user) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> opOtp = otpRepository.findOtpByUsername(user.getUsername());
        if (opOtp.isPresent()) {
            Otp otp = opOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(user.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> opOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if (opOtp.isPresent()) {
            Otp otp = opOtp.get();
            return otpToValidate.getCode().equals(otp.getCode());
        }
        return false;
    }
}
