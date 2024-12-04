package com.example.p_project.domain.Category.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 정보를 저장하는 엔티티 클래스
 * 데이터베이스의 'categories' 테이블과 매핑됩니다.
 */
@Entity                     // JPA 엔티티임을 명시
@Table(name = "categories") // 데이터베이스 테이블 이름을 'categories'로 지정
@Getter                    // Lombok: 모든 필드의 Getter 메서드 자동 생성
@Setter                    // Lombok: 모든 필드의 Setter 메서드 자동 생성
@NoArgsConstructor         // Lombok: 매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor        // Lombok: 모든 필드를 매개변수로 받는 생성자 자동 생성
public class Category {
    /**
     * 카테고리의 고유 식별자
     * 데이터베이스에서 자동으로 생성되는 Primary Key
     */
    @Id                                                     // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 증가 전략 사용
    private Long id;

    /**
     * 카테고리 이름
     * null 값을 허용하지 않으며, 중복된 이름을 가질 수 없음
     */
    @Column(nullable = false, unique = true)    // not null 제약조건과 유니크 제약조건 설정
    private String name;

    /**
     * 카테고리 설명
     * 선택적 필드로, null 값 허용
     */
    @Column
    private String description;
}
