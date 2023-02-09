package com.example.config;

import com.example.security.filter.InitialAuthFilter;
import com.example.security.filter.JwtAuthFilter;
import com.example.security.provider.OtpAuthenticationProvider;
import com.example.security.provider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final InitialAuthFilter initialAuthFilter;
    private final JwtAuthFilter jwtAuthFilter;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.csrf().disable();
        http
                .addFilterAt(initialAuthFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
