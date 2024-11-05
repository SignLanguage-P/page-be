package com.example.p_project.domain.Quiz.dto.request;

import com.example.p_project.domain.Quiz.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDTO {
    private Long wordId;
    private String question;
    private String correctAnswer;
    private String option1;
    private String option2;
    private String option3;
    private Quiz.Difficulty difficulty;
}
