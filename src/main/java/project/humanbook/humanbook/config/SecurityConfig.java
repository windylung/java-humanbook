package project.humanbook.humanbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import project.humanbook.humanbook.domain.MemberRole;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RequestLoggingFilter requestLoggingFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/join", "/api/login").permitAll()
                        .requestMatchers("/admin").hasRole(MemberRole.ADMIN.name())
                        .requestMatchers("/info").hasAnyRole(MemberRole.ADMIN.name(), MemberRole.USER.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin((auth) -> auth
                        .loginProcessingUrl("/api/loginProc")
                        .successHandler(authenticationSuccessHandler()) // 로그인 성공 시 핸들러 설정
                        .failureHandler(authenticationFailureHandler()) // 로그인 실패 시 핸들러 설정
                        .usernameParameter("loginId") // usernameParameter 설정
                        .passwordParameter("password") // passwordParameter 설정
                        .permitAll()
                )
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                )
                .csrf((auth) -> auth.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": \"true\"}");
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Login failed\"}");
        };
    }
}
