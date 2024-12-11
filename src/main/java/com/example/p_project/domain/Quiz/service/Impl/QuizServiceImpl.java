package com.example.p_project.domain.Quiz.service.Impl;
import com.example.p_project.domain.Quiz.dto.request.QuizRequestDTO;
import com.example.p_project.domain.Quiz.dto.response.QuizResponseDTO;
import com.example.p_project.domain.Quiz.entity.Quiz;
import com.example.p_project.domain.Quiz.repository.QuizRepository;
import com.example.p_project.domain.Quiz.service.QuizService;
import com.example.p_project.domain.Word.entity.Word;
import com.example.p_project.domain.Word.repository.WordRepository;
import jakarta.transaction.Transactional;
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
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final WordRepository wordRepository;

    /**
     * QuizServiceImpl의 생성자입니다.
     *
     * @param quizRepository 퀴즈 레포지토리
     * @param wordRepository 단어 레포지토리
     */
    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, WordRepository wordRepository) {
        this.quizRepository = quizRepository;
        this.wordRepository = wordRepository;
    }

    /**
     * 새로운 퀴즈를 생성합니다.
     *
     * @param quizRequestDTO 생성할 퀴즈의 정보를 담은 DTO
     * @return 생성된 퀴즈의 정보를 담은 DTO
     * @throws RuntimeException 연관된 단어를 찾을 수 없는 경우
     */
    @Override
    public QuizResponseDTO createQuiz(QuizRequestDTO quizRequestDTO) {
        // 연관된 단어 조회
        Word word = wordRepository.findById(quizRequestDTO.getWordId())
                .orElseThrow(() -> new RuntimeException("Word not found"));

        // 퀴즈 엔티티 생성
        Quiz quiz = Quiz.builder()
                .word(word)
                .question(quizRequestDTO.getQuestion())
                .correctAnswer(quizRequestDTO.getCorrectAnswer())
                .option1(quizRequestDTO.getOption1())
                .option2(quizRequestDTO.getOption2())
                .option3(quizRequestDTO.getOption3())
                .difficulty(quizRequestDTO.getDifficulty())
                .build();

        // 퀴즈 저장 및 응답 DTO 반환
        Quiz savedQuiz = quizRepository.save(quiz);
        return convertToResponseDTO(savedQuiz);
    }

    /**
     * 특정 ID의 퀴즈를 조회합니다.
     *
     * @param id 조회할 퀴즈의 ID
     * @return 조회된 퀴즈의 정보를 담은 DTO
     * @throws RuntimeException 해당 ID의 퀴즈를 찾을 수 없는 경우
     */
    @Override
    public QuizResponseDTO getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional.map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    /**
     * 모든 퀴즈를 조회합니다.
     *
     * @return 모든 퀴즈의 정보를 담은 DTO 리스트
     */
    @Override
    public List<QuizResponseDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID의 퀴즈를 수정합니다.
     *
     * @param id             수정할 퀴즈의 ID
     * @param quizRequestDTO 수정할 퀴즈의 새로운 정보를 담은 DTO
     * @return 수정된 퀴즈의 정보를 담은 DTO
     * @throws RuntimeException 해당 ID의 퀴즈나 연관된 단어를 찾을 수 없는 경우
     */
    @Override
    public QuizResponseDTO updateQuiz(Long id, QuizRequestDTO quizRequestDTO) {
        // 기존 퀴즈 조회
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // 연관된 단어 업데이트
        quiz.setWord(wordRepository.findById(quizRequestDTO.getWordId())
                .orElseThrow(() -> new RuntimeException("Word not found")));

        // 퀴즈 속성 업데이트
        setQuizProperties(quiz, quizRequestDTO);

        // 퀴즈 저장 및 응답 DTO 반환
        Quiz updatedQuiz = quizRepository.save(quiz);
        return convertToResponseDTO(updatedQuiz);
    }

    /**
     * 특정 ID의 퀴즈를 삭제합니다.
     *
     * @param id 삭제할 퀴즈의 ID
     */
    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    /**
     * 특정 카테고리와 난이도에 해당하는 랜덤 퀴즈들을 조회합니다.
     *
     * @param category   조회할 퀴즈의 카테고리
     * @param difficulty 조회할 퀴즈의 난이도
     * @param count      조회할 퀴즈의 개수
     * @return 랜덤하게 선택된 퀴즈들의 정보를 담은 DTO 리스트
     */
//    @Override
//    public List<QuizResponseDTO> getRandomQuizzes(String category, Quiz.Difficulty difficulty, int count) {
//        // 네이티브 쿼리를 통한 랜덤 퀴즈 조회
//        List<Quiz> quizzes = quizRepository.findRandomQuizzes(
//                category,
//                difficulty.name(),
//                count
//        );
//
//        // 퀴즈 엔티티들을 DTO로 변환하여 반환
//        return quizzes.stream()
//                .map(this::convertToResponseDTO)
//                .collect(Collectors.toList());
//    }

    /**
     * Quiz 엔티티를 QuizResponseDTO로 변환합니다.
     *
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
     * Quiz 엔티티의 속성들을 업데이트합니다.
     *
     * @param quiz 속성을 업데이트할 Quiz 엔티티
     * @param dto  업데이트할 속성값들을 담고 있는 DTO
     */
    private void setQuizProperties(Quiz quiz, QuizRequestDTO dto) {
        quiz.setQuestion(dto.getQuestion());
        quiz.setCorrectAnswer(dto.getCorrectAnswer());
        quiz.setOption1(dto.getOption1());
        quiz.setOption2(dto.getOption2());
        quiz.setOption3(dto.getOption3());
        quiz.setDifficulty(dto.getDifficulty());
    }


    //특정 난이도의 퀴즈들 중 지정된 개수만큼 랜덤하게 선택합니다.
    @Override
    public List<QuizResponseDTO> getAllQuizzesByDifficulty(Quiz.Difficulty difficulty) {
        List<Quiz> quizzes = quizRepository.findByDifficulty(difficulty);
        Collections.shuffle(quizzes); // 랜덤 정렬
        return quizzes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizResponseDTO> getAllQuizzesByCategory(String category) {
        List<Quiz> quizzes = quizRepository.findByCategory(category);
        Collections.shuffle(quizzes); // 랜덤 정렬
        return quizzes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}
