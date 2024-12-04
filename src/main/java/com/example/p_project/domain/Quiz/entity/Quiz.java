package com.example.p_project.domain.Quiz.entity;

import com.example.p_project.domain.Word.entity.Word;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 퀴즈 정보를 저장하는 엔티티 클래스
 * 단어와 관련된 퀴즈 문제, 정답, 보기 등의 정보를 관리합니다.
 */
@Entity  // JPA 엔티티임을 명시
@Table(name = "quizzes")  // 데이터베이스 테이블 이름 지정
@Getter  // Lombok: Getter 메서드 자동 생성
@Setter  // Lombok: Setter 메서드 자동 생성
@NoArgsConstructor  // Lombok: 기본 생성자 자동 생성
@AllArgsConstructor // Lombok: 모든 필드를 파라미터로 받는 생성자 자동 생성
public class Quiz {
    /**
     * 퀴즈의 고유 식별자
     * 자동 증가(AUTO INCREMENT) 전략 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 퀴즈와 연관된 단어 엔티티
     * 다대일(N:1) 관계 설정
     * LAZY 로딩을 통해 성능 최적화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    /**
     * 퀴즈 문제 내용
     * null 값을 허용하지 않음
     * 예: "다음 중 'Apple'의 의미로 올바른 것은?"
     */
    @Column(nullable = false)
    private String question;

    /**
     * 퀴즈의 정답
     * null 값을 허용하지 않음
     * 보기 중 정답에 해당하는 텍스트
     */
    @Column(nullable = false)
    private String correctAnswer;

    /**
     * 첫 번째 오답 보기
     * null 값을 허용하지 않음
     * 정답이 아닌 선택지
     */
    @Column(nullable = false)
    private String option1;

    /**
     * 두 번째 오답 보기
     * null 값을 허용하지 않음
     * 정답이 아닌 선택지
     */
    @Column(nullable = false)
    private String option2;

    /**
     * 세 번째 오답 보기
     * null 값을 허용하지 않음
     * 정답이 아닌 선택지
     */
    @Column(nullable = false)
    private String option3;

    /**
     * 퀴즈의 난이도
     * BEGINNER, INTERMEDIATE, ADVANCED 중 하나의 값을 가짐
     * EnumType.STRING으로 설정하여 데이터베이스에 문자열로 저장
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    /**
     * 퀴즈 난이도를 정의하는 열거형
     * BEGINNER: 초급 난이도
     * INTERMEDIATE: 중급 난이도
     * ADVANCED: 고급 난이도
     */
    public enum Difficulty {
        BEGINNER,      // 초급
        INTERMEDIATE,  // 중급
        ADVANCED      // 고급
    }
}
