package com.example.p_project.domain.Quiz.dto.response;

import com.example.p_project.domain.Quiz.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 퀴즈 정보를 클라이언트에게 응답하기 위한 DTO (Data Transfer Object) 클래스
 * 퀴즈의 상세 정보와 연관된 단어 정보를 포함합니다.
 */
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 자동 생성
public class QuizResponseDTO {
    /**
     * 퀴즈의 고유 식별자
     * 데이터베이스에서 부여된 퀴즈의 ID입니다.
     */
    private Long id;

    /**
     * 퀴즈와 연관된 단어의 고유 식별자
     * Word 엔티티와의 관계를 나타냅니다.
     */
    private Long wordId;

    /**
     * 퀴즈의 대상이 되는 단어 내용
     * 예: "Apple", "Computer" 등
     */
    private String wordContent;

    /**
     * 퀴즈 문제 내용
     * 사용자에게 표시될 실제 질문 텍스트입니다.
     * 예: "다음 중 'Apple'의 의미로 올바른 것은?"
     */
    private String question;

    /**
     * 퀴즈의 정답
     * 여러 보기 중 정답에 해당하는 텍스트입니다.
     */
    private String correctAnswer;

    /**
     * 첫 번째 오답 보기
     * 정답이 아닌 선택지로 사용됩니다.
     */
    private String option1;

    /**
     * 두 번째 오답 보기
     * 정답이 아닌 선택지로 사용됩니다.
     */
    private String option2;

    /**
     * 세 번째 오답 보기
     * 정답이 아닌 선택지로 사용됩니다.
     */
    private String option3;

    /**
     * 퀴즈의 난이도
     * Quiz.Difficulty 열거형을 사용하여 난이도를 표시합니다.
     * (예: EASY, MEDIUM, HARD)
     */
    private Quiz.Difficulty difficulty;

    /**
     * 퀴즈가 속한 카테고리
     * 연관된 단어의 카테고리 정보입니다.
     * 예: "과일", "동물", "직업" 등
     */
    private String category;
}
