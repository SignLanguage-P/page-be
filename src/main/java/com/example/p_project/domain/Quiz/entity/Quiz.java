package com.example.p_project.domain.Quiz.entity;

import com.example.p_project.domain.Word.entity.Word;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;  // 퀴즈와 관련된 단어

    @Column(nullable = false)
    private String question;  // 퀴즈 질문

    @Column(nullable = false)
    private String correctAnswer;  // 정답

    @Column(nullable = false)
    private String option1;  // 선택지 1

    @Column(nullable = false)
    private String option2;  // 선택지 2

    @Column(nullable = false)
    private String option3;  // 선택지 3

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;  // 난이도: BEGINNER, INTERMEDIATE, ADVANCED

    // 난이도 열거형
    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
