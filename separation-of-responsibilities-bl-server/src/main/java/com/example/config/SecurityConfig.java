package com.example.config;

import com.example.security.filter.InitialAuthFilter;
import com.example.security.filter.JwtAuthFilter;
import com.example.security.provider.OtpAuthenticationProvider;
import com.example.security.provider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final InitialAuthFilter initialAuthFilter;
    private final JwtAuthFilter jwtAuthFilter;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.csrf().disable();
        http.addFilterAt(
                        initialAuthFilter,
                        BasicAuthenticationFilter.class)
                .addFilterAfter(
                        jwtAuthFilter,
                        BasicAuthenticationFilter.class
                );
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
