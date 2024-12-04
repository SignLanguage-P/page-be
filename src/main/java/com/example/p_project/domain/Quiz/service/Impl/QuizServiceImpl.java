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

/**
 * 퀴즈 서비스의 구현 클래스입니다.
 * 퀴즈와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final WordRepository wordRepository;

    /**
     * 생성자를 통한 의존성 주입
     * @param quizRepository 퀴즈 데이터 접근을 위한 리포지토리
     * @param wordRepository 단어 데이터 접근을 위한 리포지토리
     */
    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, WordRepository wordRepository) {
        this.quizRepository = quizRepository;
        this.wordRepository = wordRepository;
    }

    /**
     * 새로운 퀴즈를 생성합니다.
     * @param quizRequestDTO 퀴즈 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 퀴즈의 정보를 담은 ResponseDTO
     * @throws RuntimeException 연관된 단어를 찾을 수 없는 경우
     */
    @Override
    public QuizResponseDTO createQuiz(QuizRequestDTO quizRequestDTO) {
        Quiz quiz = new Quiz();
        quiz.setWord(wordRepository.findById(quizRequestDTO.getWordId())
                .orElseThrow(() -> new RuntimeException("Word not found")));
        // 퀴즈 정보 설정
        setQuizProperties(quiz, quizRequestDTO);

        Quiz savedQuiz = quizRepository.save(quiz);
        return convertToResponseDTO(savedQuiz);
    }

    /**
     * ID로 퀴즈를 조회합니다.
     * @param id 조회할 퀴즈의 ID
     * @return 조회된 퀴즈의 정보를 담은 ResponseDTO
     * @throws RuntimeException 퀴즈를 찾을 수 없는 경우
     */
    @Override
    public QuizResponseDTO getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional.map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    /**
     * 모든 퀴즈를 조회합니다.
     * @return 모든 퀴즈의 정보를 담은 ResponseDTO 리스트
     */
    @Override
    public List<QuizResponseDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 기존 퀴즈를 수정합니다.
     * @param id 수정할 퀴즈의 ID
     * @param quizRequestDTO 수정할 퀴즈 정보를 담은 DTO
     * @return 수정된 퀴즈의 정보를 담은 ResponseDTO
     * @throws RuntimeException 퀴즈나 단어를 찾을 수 없는 경우
     */
    @Override
    public QuizResponseDTO updateQuiz(Long id, QuizRequestDTO quizRequestDTO) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quiz.setWord(wordRepository.findById(quizRequestDTO.getWordId())
                .orElseThrow(() -> new RuntimeException("Word not found")));
        // 퀴즈 정보 업데이트
        setQuizProperties(quiz, quizRequestDTO);

        Quiz updatedQuiz = quizRepository.save(quiz);
        return convertToResponseDTO(updatedQuiz);
    }

    /**
     * 퀴즈를 삭제합니다.
     * @param id 삭제할 퀴즈의 ID
     */
    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    /**
     * 특정 카테고리와 난이도에서 랜덤하게 퀴즈를 선택합니다.
     * @param category 퀴즈 카테고리
     * @param difficulty 퀴즈 난이도
     * @param count 선택할 퀴즈 개수
     * @return 선택된 퀴즈들의 정보를 담은 ResponseDTO 리스트
     */
    @Override
    public List<QuizResponseDTO> getRandomQuizzes(String category, Quiz.Difficulty difficulty, int count) {
        List<Quiz> quizzes = quizRepository.findByCategoryAndDifficulty(category, difficulty);
        Collections.shuffle(quizzes);  // 퀴즈 목록을 랜덤하게 섞음
        return quizzes.stream()
                .limit(count)  // 요청된 개수만큼만 선택
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Quiz 엔티티를 QuizResponseDTO로 변환합니다.
     * @param quiz 변환할 Quiz 엔티티
     * @return 변환된 QuizResponseDTO
     */
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

    /**
     * Quiz 엔티티의 속성들을 설정합니다.
     * @param quiz 속성을 설정할 Quiz 엔티티
     * @param dto 설정할 속성값들을 담고 있는 DTO
     */
    private void setQuizProperties(Quiz quiz, QuizRequestDTO dto) {
        quiz.setQuestion(dto.getQuestion());
        quiz.setCorrectAnswer(dto.getCorrectAnswer());
        quiz.setOption1(dto.getOption1());
        quiz.setOption2(dto.getOption2());
        quiz.setOption3(dto.getOption3());
        quiz.setDifficulty(dto.getDifficulty());
    }
}
