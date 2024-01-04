package com.web.albion.Security;

import com.web.albion.Service.ConnectLogService;
import com.web.albion.dto.ConnectLogDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

    @Autowired
    private ConnectLogService connectlogservice;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/", "/match/**", LOGIN_PATH).permitAll()
                .requestMatchers("/admin/board/start", "/admin/board","/admin/board/stop").authenticated()
                .and()
                //.csrf().disable()
                .formLogin(form -> form
                        .loginPage(LOGIN_PATH)
                        .usernameParameter(USERNAME_PARAMETER)
                        .passwordParameter(PASSWORD_PARAMETER)
                        .successHandler(sHandler)
                        .failureHandler(fHandler))
                .authenticationProvider(provider)
                .addFilterBefore(new IPLoggingFilter(connectlogservice), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

class IPLoggingFilter extends OncePerRequestFilter {

    private ConnectLogService connectlogservice;

    public IPLoggingFilter(ConnectLogService connectlogservice) {
        this.connectlogservice = connectlogservice;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 클라이언트의 IP 주소 가져오기
        String clientIP = request.getRemoteAddr();

        // 여기에서 로깅을 수행하거나 로그에 IP를 추가하는 작업 수행
        // 예를 들어, Logger 사용 시:
        // System.out.println(clientIP);
        //System.out.println(request.getRequestURL().toString());
        // System.out.println(request.getQueryString().toString());

        // System.out.println(request.getRequestURI().toString());

        ConnectLogDto connectlog = new ConnectLogDto();


        String requestURI = request.getRequestURI() + "/?" + request.getQueryString();
        // System.out.println(requestURI);

        connectlog.setRemote_ip(clientIP);
        connectlog.setRequest_uri(requestURI);

        this.connectlogservice.insertConnectLog(connectlog);

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}