package com.example.p_project.domain.Quiz.service.Impl;
import com.example.p_project.domain.Quiz.dto.request.QuizRequestDTO;
import com.example.p_project.domain.Quiz.dto.response.QuizResponseDTO;
import com.example.p_project.domain.Quiz.entity.Quiz;
import com.example.p_project.domain.Quiz.repository.QuizRepository;
import com.example.p_project.domain.Quiz.service.QuizService;
import com.example.p_project.domain.Word.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final WordRepository wordRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, WordRepository wordRepository) {
        this.quizRepository = quizRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public QuizResponseDTO createQuiz(QuizRequestDTO quizRequestDTO) {
        Quiz quiz = new Quiz();
        quiz.setWord(wordRepository.findById(quizRequestDTO.getWordId()).orElseThrow(() -> new RuntimeException("Word not found")));
        quiz.setQuestion(quizRequestDTO.getQuestion());
        quiz.setCorrectAnswer(quizRequestDTO.getCorrectAnswer());
        quiz.setOption1(quizRequestDTO.getOption1());
        quiz.setOption2(quizRequestDTO.getOption2());
        quiz.setOption3(quizRequestDTO.getOption3());
        quiz.setDifficulty(quizRequestDTO.getDifficulty());

        Quiz savedQuiz = quizRepository.save(quiz);
        return convertToResponseDTO(savedQuiz);
    }

    @Override
    public QuizResponseDTO getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional.map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    public List<QuizResponseDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuizResponseDTO updateQuiz(Long id, QuizRequestDTO quizRequestDTO) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quiz.setWord(wordRepository.findById(quizRequestDTO.getWordId()).orElseThrow(() -> new RuntimeException("Word not found")));
        quiz.setQuestion(quizRequestDTO.getQuestion());
        quiz.setCorrectAnswer(quizRequestDTO.getCorrectAnswer());
        quiz.setOption1(quizRequestDTO.getOption1());
        quiz.setOption2(quizRequestDTO.getOption2());
        quiz.setOption3(quizRequestDTO.getOption3());
        quiz.setDifficulty(quizRequestDTO.getDifficulty());

        Quiz updatedQuiz = quizRepository.save(quiz);
        return convertToResponseDTO(updatedQuiz);
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public List<QuizResponseDTO> getRandomQuizzes(String category, Quiz.Difficulty difficulty, int count) {
        List<Quiz> quizzes = quizRepository.findByCategoryAndDifficulty(category, difficulty);
        Collections.shuffle(quizzes);  // 퀴즈 목록을 랜덤하게 섞습니다.
        return quizzes.stream()
                .limit(count)  // 요청된 개수만큼만 선택합니다.
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private QuizResponseDTO convertToResponseDTO(Quiz quiz) {
        return new QuizResponseDTO(
                quiz.getId(),
                quiz.getWord().getId(),
                quiz.getWord().getContent(),
                quiz.getQuestion(),
                quiz.getCorrectAnswer(),
                quiz.getOption1(),
                quiz.getOption2(),
                quiz.getOption3(),
                quiz.getDifficulty(),
                quiz.getWord().getCategory().getName()
        );
    }
}
