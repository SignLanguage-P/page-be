package com.example.p_project.domain.Quiz;

import com.example.p_project.domain.Category.entity.Category;
import com.example.p_project.domain.Quiz.dto.request.QuizRequestDTO;
import com.example.p_project.domain.Quiz.dto.response.QuizResponseDTO;
import com.example.p_project.domain.Quiz.entity.Quiz;
import com.example.p_project.domain.Quiz.repository.QuizRepository;
import com.example.p_project.domain.Quiz.service.Impl.QuizServiceImpl;
import com.example.p_project.domain.Word.entity.Word;
import com.example.p_project.domain.Word.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat; // AssertionsForClassTypes 대신 Assertions 사용
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * QuizService 구현체에 대한 단위 테스트 클래스입니다.
 * 퀴즈 관련 비즈니스 로직을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {
    // Mockito를 사용하여 의존성을 모의(Mock) 객체로 대체
    @Mock
    private QuizRepository quizRepository;  // 퀴즈 저장소 모의 객체

    @Mock
    private WordRepository wordRepository;  // 단어 저장소 모의 객체

    @InjectMocks
    private QuizServiceImpl quizService;    // 테스트 대상 서비스

    // 테스트에 사용될 객체들
    private Category category;  // 카테고리 객체
    private Word word;         // 단어 객체
    private Quiz quiz;         // 퀴즈 객체
    private QuizRequestDTO quizRequestDTO;  // 퀴즈 생성/수정 요청 DTO

    /**
     * 각 테스트 메소드 실행 전에 실행되는 설정 메소드
     * 테스트에 필요한 기본 데이터를 초기화합니다.
     */
    @BeforeEach
    void setUp() {
        // 테스트용 카테고리 객체 생성
        category = Category.builder()
                .name("테스트 카테고리")
                .description("테스트 설명")
                .build();

        // 테스트용 단어 객체 생성
        word = Word.builder()
                .content("테스트 단어")
                .description("테스트 설명")
                .category(category)
                .videoUrl("https://example.com/video.mp4")
                .build();

        // 테스트용 퀴즈 객체 생성
        quiz = Quiz.builder()
                .word(word)
                .question("테스트 질문")
                .correctAnswer("정답")
                .option1("오답1")
                .option2("오답2")
                .option3("오답3")
                .difficulty(Quiz.Difficulty.BEGINNER)
                .build();

        // 테스트용 퀴즈 요청 DTO 생성
        quizRequestDTO = new QuizRequestDTO();
        quizRequestDTO.setWordId(1L);
        quizRequestDTO.setQuestion("테스트 질문");
        quizRequestDTO.setCorrectAnswer("정답");
        quizRequestDTO.setOption1("오답1");
        quizRequestDTO.setOption2("오답2");
        quizRequestDTO.setOption3("오답3");
        quizRequestDTO.setDifficulty(Quiz.Difficulty.BEGINNER);
    }

    /**
     * 퀴즈 생성 기능을 테스트합니다.
     * 새로운 퀴즈가 정상적으로 생성되는지 확인합니다.
     */
    @Test
    @DisplayName("퀴즈 생성 테스트")
    void createQuizTest() {
        // given: 테스트 준비
        when(wordRepository.findById(any())).thenReturn(Optional.of(word));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        // when: 테스트 실행
        QuizResponseDTO result = quizService.createQuiz(quizRequestDTO);

        // then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo(quiz.getQuestion());
        assertThat(result.getCorrectAnswer()).isEqualTo(quiz.getCorrectAnswer());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    /**
     * ID로 퀴즈를 조회하는 기능을 테스트합니다.
     * 특정 ID의 퀴즈가 정상적으로 조회되는지 확인합니다.
     */
    @Test
    @DisplayName("ID로 퀴즈 조회 테스트")
    void getQuizByIdTest() {
        // given: 테스트 준비
        Long quizId = 1L;
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        // when: 테스트 실행
        QuizResponseDTO result = quizService.getQuizById(quizId);

        // then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo(quiz.getQuestion());
        verify(quizRepository, times(1)).findById(quizId);
    }

    /**
     * 존재하지 않는 퀴즈 조회 시 예외 발생을 테스트합니다.
     * 적절한 예외가 발생하는지 확인합니다.
     */
    @Test
    @DisplayName("존재하지 않는 퀴즈 조회 시 예외 발생 테스트")
    void getQuizByIdNotFoundTest() {
        // given: 테스트 준비
        Long quizId = 999L;
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        // when & then: 테스트 실행 및 결과 검증
        assertThatThrownBy(() -> quizService.getQuizById(quizId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Quiz not found");
    }

    /**
     * 모든 퀴즈 조회 기능을 테스트합니다.
     * 전체 퀴즈 목록이 정상적으로 조회되는지 확인합니다.
     */
    @Test
    @DisplayName("모든 퀴즈 조회 테스트")
    void getAllQuizzesTest() {
        // given: 테스트 준비
        List<Quiz> quizzes = List.of(quiz);
        when(quizRepository.findAll()).thenReturn(quizzes);

        // when: 테스트 실행
        List<QuizResponseDTO> results = quizService.getAllQuizzes();

        // then: 결과 검증
        assertThat(results)
                .isNotNull()
                .hasSize(1);
        assertThat(results.get(0).getQuestion()).isEqualTo(quiz.getQuestion());
        verify(quizRepository).findAll();
    }

    /**
     * 퀴즈 업데이트 기능을 테스트합니다.
     * 기존 퀴즈가 정상적으로 수정되는지 확인합니다.
     */
    @Test
    @DisplayName("퀴즈 업데이트 테스트")
    void updateQuizTest() {
        // given: 테스트 준비
        Long quizId = 1L;
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(wordRepository.findById(any())).thenReturn(Optional.of(word));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        // when: 테스트 실행
        QuizResponseDTO result = quizService.updateQuiz(quizId, quizRequestDTO);

        // then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo(quizRequestDTO.getQuestion());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    /**
     * 퀴즈 삭제 기능을 테스트합니다.
     * 퀴즈가 정상적으로 삭제되는지 확인합니다.
     */
    @Test
    @DisplayName("퀴즈 삭제 테스트")
    void deleteQuizTest() {
        // given: 테스트 준비
        Long quizId = 1L;
        doNothing().when(quizRepository).deleteById(quizId);

        // when: 테스트 실행
        quizService.deleteQuiz(quizId);

        // then: 결과 검증
        verify(quizRepository, times(1)).deleteById(quizId);
    }

    /**
     * 랜덤 퀴즈 조회 기능을 테스트합니다.
     * 지정된 조건에 맞는 랜덤 퀴즈들이 정상적으로 조회되는지 확인합니다.
     */
    @Test
    @DisplayName("랜덤 퀴즈 조회 테스트")
    void getRandomQuizzesTest() {
        // given: 테스트 준비
        String categoryName = "테스트 카테고리";
        Quiz.Difficulty difficulty = Quiz.Difficulty.BEGINNER;
        int count = 5;
        List<Quiz> randomQuizzes = List.of(quiz);

        when(quizRepository.findRandomQuizzes(
                eq(categoryName),
                eq(difficulty.name()),
                eq(count)
        )).thenReturn(randomQuizzes);

        // when: 테스트 실행
        List<QuizResponseDTO> results = quizService.getRandomQuizzes(categoryName, difficulty, count);

        // then: 결과 검증
        assertThat(results)
                .isNotNull()
                .hasSize(1);
        assertThat(results.get(0).getQuestion()).isEqualTo(quiz.getQuestion());
        verify(quizRepository).findRandomQuizzes(
                eq(categoryName),
                eq(difficulty.name()),
                eq(count)
        );
    }
}
