package com.example.p_project.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 기능을 활성화하기 위한 설정 클래스
 * 엔티티의 생성시간, 수정시간을 자동으로 관리합니다.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

}
