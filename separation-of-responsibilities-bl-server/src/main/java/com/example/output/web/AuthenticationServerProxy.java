package com.example.output.web;

import com.example.output.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {

    @Value("${auth.server.base.url}")
    private String baseUrl;

    private final RestTemplate authServerClient;

    public void sendAuth(String username,
                         String password) {
        String url = baseUrl + "/user/auth";
        var body = new User();
        body.setUsername(username);
        body.setPassword(password);
        var request = new HttpEntity<>(body);
        authServerClient.postForEntity(url, request, Void.class);
    }

    public boolean sendOTP(String username,
                           String code) {
        String url = baseUrl + "/otp/check";
        var body = new User();
        body.setUsername(username);
        body.setCode(code);
        var request = new HttpEntity<>(body);
        var response = authServerClient.postForEntity(url, request, Void.class);
        return response
                .getStatusCode()
                .equals(HttpStatus.OK);
    }

}
