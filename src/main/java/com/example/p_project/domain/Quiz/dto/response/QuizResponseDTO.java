package com.example.p_project.domain.Quiz.dto.response;

import com.example.p_project.domain.Quiz.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDTO {
    private Long id;
    private Long wordId;
    private String wordContent;
    private String question;
    private String correctAnswer;
    private String option1;
    private String option2;
    private String option3;
    private Quiz.Difficulty difficulty;
    private String category;  // 단어의 카테고리
}
