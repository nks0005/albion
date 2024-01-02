package com.web.albion.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String defaultUrl = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        SimpleGrantedAuthority authroAuthoriy =
                (SimpleGrantedAuthority) ((List<GrantedAuthority>) authentication.getAuthorities()).get(0);

        switch (authroAuthoriy.getAuthority()) {
            case "admin":
                defaultUrl = "/admin/board";
                break;
            case "user":
                defaultUrl = "/";
                break;
        }

        response.setStatus(200);
        response.sendRedirect(defaultUrl);
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}
