package com.example.p_project.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 이 클래스가 스프링의 설정 클래스임을 나타냄
@Configuration
// Spring Security 설정을 활성화
@EnableWebSecurity
// final 필드에 대한 생성자를 자동으로 생성
@RequiredArgsConstructor
public class SecurityConfig {
    // 비밀번호 암호화를 위한 BCrypt 인코더를 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt 해싱 함수를 사용하여 비밀번호를 암호화
    }

    // Spring Security의 필터 체인을 구성하는 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")  // 모든 요청에 대해 이 설정을 적용
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // 모든 요청 허용
                )
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .cors(cors -> cors.disable())  // CORS 비활성화
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));  // Frame 옵션 비활성화

        return http.build();
    }
}
