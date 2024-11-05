package com.example.p_project.domain.Quiz.service;

import com.example.p_project.domain.Quiz.dto.request.QuizRequestDTO;
import com.example.p_project.domain.Quiz.dto.response.QuizResponseDTO;
import com.example.p_project.domain.Quiz.entity.Quiz;

import java.util.List;

public interface QuizService {
    /**
     * 새로운 퀴즈를 생성합니다.
     * @param quizRequestDTO 생성할 퀴즈의 정보
     * @return 생성된 퀴즈의 정보
     */
    QuizResponseDTO createQuiz(QuizRequestDTO quizRequestDTO);

    /**
     * 주어진 ID에 해당하는 퀴즈를 조회합니다.
     * @param id 조회할 퀴즈의 ID
     * @return 조회된 퀴즈의 정보
     */
    QuizResponseDTO getQuizById(Long id);

    /**
     * 모든 퀴즈를 조회합니다.
     * @return 모든 퀴즈의 목록
     */
    List<QuizResponseDTO> getAllQuizzes();

    /**
     * 주어진 ID에 해당하는 퀴즈를 업데이트합니다.
     * @param id 업데이트할 퀴즈의 ID
     * @param quizRequestDTO 업데이트할 퀴즈의 새로운 정보
     * @return 업데이트된 퀴즈의 정보
     */
    QuizResponseDTO updateQuiz(Long id, QuizRequestDTO quizRequestDTO);

    /**
     * 주어진 ID에 해당하는 퀴즈를 삭제합니다.
     * @param id 삭제할 퀴즈의 ID
     */
    void deleteQuiz(Long id);

    /**
     * 주어진 카테고리와 난이도에 해당하는 랜덤 퀴즈를 조회합니다.
     * @param category 퀴즈 카테고리
     * @param difficulty 퀴즈 난이도
     * @param count 조회할 퀴즈의 개수
     * @return 랜덤하게 선택된 퀴즈 목록
     */
    List<QuizResponseDTO> getRandomQuizzes(String category, Quiz.Difficulty difficulty, int count);
}
