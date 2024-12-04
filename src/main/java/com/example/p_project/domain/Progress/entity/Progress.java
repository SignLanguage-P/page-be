package com.example.p_project.domain.Progress.entity;

import com.example.p_project.domain.User.entity.User;
import com.example.p_project.domain.Word.entity.Word;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 사용자의 단어 학습 진행 상태를 관리하는 엔티티 클래스
 * 사용자(User)와 단어(Word) 엔티티와의 다대일(N:1) 관계를 가지며,
 * 각 사용자별 단어 학습 현황을 추적하고 저장합니다.
 */
@Entity                     // JPA 엔티티임을 명시
@Table(name = "progress")  // 데이터베이스 테이블 이름 지정
@Getter                    // 모든 필드의 getter 메서드 자동 생성
@Setter                    // 모든 필드의 setter 메서드 자동 생성
@NoArgsConstructor         // 매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor        // 모든 필드를 매개변수로 받는 생성자 자동 생성
public class Progress {
    /**
     * 진행 상태의 고유 식별자
     * 데이터베이스에서 자동으로 생성되는 기본키(Primary Key)입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 학습 진행 상태의 소유자인 사용자 정보
     * User 엔티티와의 다대일(N:1) 관계를 형성합니다.
     * LAZY 로딩을 사용하여 성능을 최적화합니다.
     * nullable = false로 설정하여 필수 값으로 지정합니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 학습 대상인 단어 정보
     * Word 엔티티와의 다대일(N:1) 관계를 형성합니다.
     * LAZY 로딩을 사용하여 성능을 최적화합니다.
     * nullable = false로 설정하여 필수 값으로 지정합니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    /**
     * 단어 학습 완료 여부를 나타내는 플래그
     * true: 학습 완료
     * false: 학습 미완료
     * nullable = false로 설정하여 필수 값으로 지정합니다.
     */
    @Column(nullable = false)
    private boolean completed;

    /**
     * 사용자가 해당 단어를 마지막으로 학습한 시간
     * 학습 이력 추적 및 복습 일정 관리에 활용됩니다.
     * nullable = false로 설정하여 필수 값으로 지정합니다.
     */
    @Column(nullable = false)
    private LocalDateTime lastStudiedAt;

    /**
     * 사용자가 해당 단어를 학습한 총 횟수
     * 학습 진도와 반복 학습 현황을 파악하는데 사용됩니다.
     * nullable = false로 설정하여 필수 값으로 지정합니다.
     */
    @Column(nullable = false)
    private int studyCount;
}
