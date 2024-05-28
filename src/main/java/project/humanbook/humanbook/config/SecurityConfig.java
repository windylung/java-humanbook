package project.humanbook.humanbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import project.humanbook.humanbook.domain.MemberRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 시큐리티 필터 메서드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
        .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/join").permitAll()
                .requestMatchers("/admin").hasRole(MemberRole.ADMIN.name())
                .requestMatchers("/info").hasAnyRole(MemberRole.ADMIN.name(), MemberRole.USER.name())
                .anyRequest().permitAll()
        );

http
        .logout((auth) -> auth
                .logoutUrl("/logout")
        );

http
        .formLogin((auth) -> auth.loginPage("/login")
                .loginProcessingUrl("/loginProc")
                // 프론트단에서 설정해 둔 경로로 로그인 정보를 넘기면 스프링 시큐리티가 받아서 자동으로 로그인 진행
                .failureUrl("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("loginId")
                .passwordParameter("password")

                .permitAll()
        );


http
        .csrf((auth) -> auth.disable());
        
        return http.build();
    }

    // BCrypt password encoder를 리턴하는 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
