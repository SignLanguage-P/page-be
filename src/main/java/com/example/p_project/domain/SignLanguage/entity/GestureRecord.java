package com.example.p_project.domain.SignLanguage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 수화 제스처 인식 결과를 저장하는 엔티티
 * 제스처 인식의 예측 결과와 관련 데이터를 데이터베이스에 저장합니다.
 */
@Entity  // JPA 엔티티임을 명시
@Table(name = "gesture_records")  // 데이터베이스 테이블 이름 지정
@Data  // getter, setter, toString, equals, hashCode 메서드 자동 생성
@Builder  // 빌더 패턴 구현
@NoArgsConstructor  // 매개변수 없는 기본 생성자 생성
@AllArgsConstructor  // 모든 필드를 매개변수로 받는 생성자 생성
public class GestureRecord {
    /**
     * 레코드의 고유 식별자
     * 자동 증가(AUTO INCREMENT) 전략 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 예측된 제스처의 클래스 인덱스
     * null을 허용하지 않음
     * 범위: 0 ~ (전체 클래스 수 - 1)
     */
    @Column(nullable = false)
    private int predictedClass;

    /**
     * 예측에 대한 신뢰도 점수
     * null을 허용하지 않음
     * 범위: 0.0 ~ 1.0
     */
    @Column(nullable = false)
    private double confidence;

    /**
     * 예측된 제스처의 레이블(이름)
     * 최대 50자까지 저장 가능
     * 예: "안녕하세요", "감사합니다" 등
     */
    @Column(length = 50)
    private String gestureLabel;

    /**
     * MediaPipe에서 추출한 손 랜드마크 데이터
     * JSON 형식의 문자열로 저장
     * TEXT 타입 사용으로 대용량 데이터 저장 가능
     */
    @Column(columnDefinition = "TEXT")
    private String landmarkData;

    /**
     * 레코드 생성 시간
     * 데이터베이스 칼럼명: created_at
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 엔티티 저장 전 자동으로 실행되는 메서드
     * 생성 시간을 현재 시간으로 자동 설정
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
