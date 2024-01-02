package com.web.albion.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String LOGIN_PATH = "/admin/login";
    private static final String USERNAME_PARAMETER = "user_name";
    private static final String PASSWORD_PARAMETER = "user_password";

    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private AuthenticationSuccessHandler sHandler;

    @Autowired
    private AuthenticationFailureHandler fHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/", LOGIN_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin(form -> form
                        .loginPage(LOGIN_PATH)
                        .usernameParameter(USERNAME_PARAMETER)
                        .passwordParameter(PASSWORD_PARAMETER)
                        .successHandler(sHandler)
                        .failureHandler(fHandler))
                .authenticationProvider(provider);

        return http.build();
    }
}