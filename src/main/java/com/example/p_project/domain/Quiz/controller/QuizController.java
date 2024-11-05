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

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuiz(@RequestBody QuizRequestDTO quizRequestDTO) {
        QuizResponseDTO createdQuiz = quizService.createQuiz(quizRequestDTO);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> getQuizById(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes() {
        List<QuizResponseDTO> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> updateQuiz(@PathVariable Long id, @RequestBody QuizRequestDTO quizRequestDTO) {
        QuizResponseDTO updatedQuiz = quizService.updateQuiz(id, quizRequestDTO);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/random")
    public ResponseEntity<List<QuizResponseDTO>> getRandomQuizzes(
            @RequestParam String category,
            @RequestParam Quiz.Difficulty difficulty,
            @RequestParam(defaultValue = "10") int count) {
        List<QuizResponseDTO> quizzes = quizService.getRandomQuizzes(category, difficulty, count);
        return ResponseEntity.ok(quizzes);
    }
}
