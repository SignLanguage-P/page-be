package com.example.p_project.domain.Quiz.controller;

import com.example.p_project.domain.Quiz.dto.request.QuizRequestDTO;
import com.example.p_project.domain.Quiz.dto.response.QuizResponseDTO;
import com.example.p_project.domain.Quiz.entity.Quiz;
import com.example.p_project.domain.Quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 퀴즈 관리를 위한 REST API 컨트롤러
 * 퀴즈의 생성, 조회, 수정, 삭제 및 랜덤 퀴즈 선택 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    /**
     * QuizController 생성자
     * QuizService를 의존성 주입받아 초기화합니다.
     *
     * @param quizService 퀴즈 관련 비즈니스 로직을 처리하는 서비스
     */
    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * 새로운 퀴즈를 생성합니다.
     *
     * @param quizRequestDTO 생성할 퀴즈 정보가 담긴 DTO
     * @return ResponseEntity<QuizResponseDTO> 생성된 퀴즈 정보와 HTTP 상태 코드 201
     */
    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuiz(@RequestBody QuizRequestDTO quizRequestDTO) {
        QuizResponseDTO createdQuiz = quizService.createQuiz(quizRequestDTO);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    /**
     * ID로 특정 퀴즈를 조회합니다.
     *
     * @param id 조회할 퀴즈의 고유 식별자
     * @return ResponseEntity<QuizResponseDTO> 조회된 퀴즈 정보
     * @throws RuntimeException 해당 ID의 퀴즈가 존재하지 않는 경우
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> getQuizById(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    /**
     * 모든 퀴즈 목록을 조회합니다.
     *
     * @return ResponseEntity<List<QuizResponseDTO>> 전체 퀴즈 목록
     */
    @GetMapping
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes() {
        List<QuizResponseDTO> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    /**
     * 특정 퀴즈의 정보를 업데이트합니다.
     *
     * @param id 수정할 퀴즈의 고유 식별자
     * @param quizRequestDTO 업데이트할 퀴즈 정보가 담긴 DTO
     * @return ResponseEntity<QuizResponseDTO> 업데이트된 퀴즈 정보
     * @throws RuntimeException 해당 ID의 퀴즈가 존재하지 않는 경우
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> updateQuiz(@PathVariable Long id, @RequestBody QuizRequestDTO quizRequestDTO) {
        QuizResponseDTO updatedQuiz = quizService.updateQuiz(id, quizRequestDTO);
        return ResponseEntity.ok(updatedQuiz);
    }

    /**
     * 특정 퀴즈를 삭제합니다.
     *
     * @param id 삭제할 퀴즈의 고유 식별자
     * @return ResponseEntity<Void> 삭제 완료 시 HTTP 상태 코드 204
     * @throws RuntimeException 해당 ID의 퀴즈가 존재하지 않는 경우
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 지정된 조건에 맞는 랜덤 퀴즈 목록을 조회합니다.
     *
     * @param category 퀴즈 카테고리
     * @param difficulty 퀴즈 난이도 (EASY, MEDIUM, HARD 등)
     * @param count 조회할 퀴즈 개수 (기본값: 10)
     * @return ResponseEntity<List<QuizResponseDTO>> 조건에 맞는 랜덤 퀴즈 목록
     */
    @GetMapping("/random")
    public ResponseEntity<List<QuizResponseDTO>> getRandomQuizzes(
            @RequestParam String category,
            @RequestParam Quiz.Difficulty difficulty,
            @RequestParam(defaultValue = "10") int count) {
        List<QuizResponseDTO> quizzes = quizService.getRandomQuizzes(category, difficulty, count);
        return ResponseEntity.ok(quizzes);
    }
}
