package com.web.albion.Security;

import com.web.albion.Service.UsersService;
import com.web.albion.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsersService userService;

    private String keyCode = "albion_test_2024!!";

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsersDto user = (UsersDto) userService.loadUserByUsername(authentication.getName());

        if (!passwordEncoder.matches(authentication.getCredentials().toString() + keyCode, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
