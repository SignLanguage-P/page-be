package com.example.p_project.domain.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA 엔티티임을 나타냅니다.
@Table(name = "users") // 데이터베이스의 'users' 테이블과 매핑됩니다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // Lombok 어노테이션으로 기본 메서드들을 자동 생성합니다.
public class User {
    @Id // 기본 키(Primary Key)를 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다.
    private Long id;

    @Column(nullable = false, unique = true) // null 값을 허용하지 않고, 유일해야 함을 나타냅니다.
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // 사용자의 역할(권한)을 저장합니다.

    @Column(name = "progress_level")
    private Integer progressLevel; // 사용자의 학습 진행 수준을 나타냅니다.
}
