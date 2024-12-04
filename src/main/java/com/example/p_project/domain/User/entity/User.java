package com.example.p_project.domain.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 정보를 표현하는 엔티티 클래스입니다.
 * 데이터베이스의 'users' 테이블과 매핑됩니다.
 *
 * @Entity JPA 엔티티임을 나타냅니다.
 * @Table users 테이블과의 매핑을 정의합니다.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor  // 매개변수가 없는 기본 생성자를 생성합니다.
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 생성합니다.
public class User {
    /**
     * 사용자의 고유 식별자입니다.
     * 데이터베이스에서 자동으로 생성되는 기본 키입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자의 로그인 아이디입니다.
     * null이 될 수 없으며, 시스템 내에서 유일한 값이어야 합니다.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 사용자의 이메일 주소입니다.
     * null이 될 수 없으며, 시스템 내에서 유일한 값이어야 합니다.
     * 비밀번호 재설정 등의 기능에 사용됩니다.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 사용자의 비밀번호입니다.
     * 보안을 위해 암호화되어 저장됩니다.
     * null이 될 수 없습니다.
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자의 역할(권한)을 나타냅니다.
     * 예: "ROLE_USER", "ROLE_ADMIN" 등
     * 보안 및 접근 제어에 사용됩니다.
     * null이 될 수 없습니다.
     */
    @Column(nullable = false)
    private String role;

    /**
     * 사용자의 학습 진행 수준을 나타냅니다.
     * 사용자의 학습 진도와 성취도를 추적하는데 사용됩니다.
     * null이 허용됩니다.
     */
    @Column(name = "progress_level")
    private Integer progressLevel;
}
