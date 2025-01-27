package com.example.p_project.domain.Quiz.entity;

import com.example.p_project.domain.Word.entity.Word;
import jakarta.persistence.*;
import lombok.*;

/**
 * 퀴즈 정보를 저장하는 엔티티 클래스
 * 단어와 관련된 퀴즈 문제, 정답, 보기 등의 정보를 관리합니다.
 */
@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String correctAnswer;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column(nullable = false)
    private String option3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;



    public enum Difficulty {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }

    // 퀴즈 유형 enum
    public enum QuizType {
        MULTIPLE_CHOICE("객관식"),
        SIGN_VIDEO("수화 동작 맞추기"),
        WORD_MATCHING("단어 연결하기"),
        TRUE_FALSE("O/X");

        private final String koreanName;

        QuizType(String koreanName) {
            this.koreanName = koreanName;
        }

        public String getKoreanName() {
            return koreanName;
        }
    }


    @Builder
    public Quiz(Word word, String question, String correctAnswer,
                String option1, String option2, String option3, Difficulty difficulty, QuizType quizType) {
        this.word = word;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.difficulty = difficulty;
    }
}
