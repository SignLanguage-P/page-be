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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF(Cross-Site Request Forgery) 보안 기능 비활성화
                // REST API 서버는 CSRF 공격으로부터 안전하므로 비활성화 가능
                .csrf((csrf) -> csrf.disable())

                // HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests((authorize) -> authorize
                        // Swagger UI 관련 경로에 대한 접근 허용 설정
                        .requestMatchers(
                                // Swagger UI의 웹 페이지 접근 경로
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                // Swagger API 문서 경로
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                // Swagger 리소스 경로
                                new AntPathRequestMatcher("/swagger-resources/**")
                        ).permitAll()  // 위의 경로들은 인증 없이 접근 가능

                        // 위에서 설정한 경로 외의 모든 요청에 대해 인증 필요
                        .anyRequest().authenticated()
                );

        // 구성된 SecurityFilterChain 객체 반환
        return http.build();
    }
}
