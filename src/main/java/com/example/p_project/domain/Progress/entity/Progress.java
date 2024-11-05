package com.example.p_project.domain.Progress.entity;

import com.example.p_project.domain.User.entity.User;
import com.example.p_project.domain.Word.entity.Word;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 학습 진행 상황의 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;  // 학습한 단어

    @Column(nullable = false)
    private boolean completed;  // 학습 완료 여부

    @Column(nullable = false)
    private LocalDateTime lastStudiedAt;  // 마지막 학습 시간

    @Column(nullable = false)
    private int studyCount;  // 학습 횟수
}
