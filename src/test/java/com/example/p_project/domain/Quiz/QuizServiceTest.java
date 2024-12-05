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
 * QuizService 구현체에 대한 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {
    @Mock
    private QuizRepository quizRepository;

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    private Category category;
    private Word word;
    private Quiz quiz;
    private QuizRequestDTO quizRequestDTO;

    /**
     * 각 테스트 전에 실행되어 테스트에 필요한 데이터를 설정합니다.
     */
    @BeforeEach
    void setUp() {
        // 카테고리 설정
        category = Category.builder()
                .name("테스트 카테고리")
                .description("테스트 설명")
                .build();

        // 단어 설정
        word = Word.builder()
                .content("테스트 단어")
                .description("테스트 설명")
                .category(category)
                .videoUrl("https://example.com/video.mp4")
                .build();

        // 퀴즈 설정
        quiz = Quiz.builder()
                .word(word)
                .question("테스트 질문")
                .correctAnswer("정답")
                .option1("오답1")
                .option2("오답2")
                .option3("오답3")
                .difficulty(Quiz.Difficulty.BEGINNER)
                .build();

        // 퀴즈 요청 DTO 설정
        quizRequestDTO = new QuizRequestDTO();
        quizRequestDTO.setWordId(1L);
        quizRequestDTO.setQuestion("테스트 질문");
        quizRequestDTO.setCorrectAnswer("정답");
        quizRequestDTO.setOption1("오답1");
        quizRequestDTO.setOption2("오답2");
        quizRequestDTO.setOption3("오답3");
        quizRequestDTO.setDifficulty(Quiz.Difficulty.BEGINNER);
    }

    @Test
    @DisplayName("퀴즈 생성 테스트")
    void createQuizTest() {
        // given
        when(wordRepository.findById(any())).thenReturn(Optional.of(word));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        // when
        QuizResponseDTO result = quizService.createQuiz(quizRequestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo(quiz.getQuestion());
        assertThat(result.getCorrectAnswer()).isEqualTo(quiz.getCorrectAnswer());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    @DisplayName("ID로 퀴즈 조회 테스트")
    void getQuizByIdTest() {
        // given
        Long quizId = 1L;
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        // when
        QuizResponseDTO result = quizService.getQuizById(quizId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo(quiz.getQuestion());
        verify(quizRepository, times(1)).findById(quizId);
    }

    @Test
    @DisplayName("존재하지 않는 퀴즈 조회 시 예외 발생 테스트")
    void getQuizByIdNotFoundTest() {
        // given
        Long quizId = 999L;
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> quizService.getQuizById(quizId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Quiz not found");
    }

    @Test
    @DisplayName("모든 퀴즈 조회 테스트")
    void getAllQuizzesTest() {
        // given
        List<Quiz> quizzes = List.of(quiz); // Arrays.asList 대신 List.of 사용
        when(quizRepository.findAll()).thenReturn(quizzes);

        // when
        List<QuizResponseDTO> results = quizService.getAllQuizzes();

        // then
        assertThat(results)
                .isNotNull()
                .hasSize(1);
        assertThat(results.get(0).getQuestion()).isEqualTo(quiz.getQuestion());
        verify(quizRepository).findAll();
    }

    @Test
    @DisplayName("퀴즈 업데이트 테스트")
    void updateQuizTest() {
        // given
        Long quizId = 1L;
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(wordRepository.findById(any())).thenReturn(Optional.of(word));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        // when
        QuizResponseDTO result = quizService.updateQuiz(quizId, quizRequestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo(quizRequestDTO.getQuestion());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    @DisplayName("퀴즈 삭제 테스트")
    void deleteQuizTest() {
        // given
        Long quizId = 1L;
        doNothing().when(quizRepository).deleteById(quizId);

        // when
        quizService.deleteQuiz(quizId);

        // then
        verify(quizRepository, times(1)).deleteById(quizId);
    }

    @Test
    @DisplayName("랜덤 퀴즈 조회 테스트")
    void getRandomQuizzesTest() {
        // given
        String categoryName = "테스트 카테고리";
        Quiz.Difficulty difficulty = Quiz.Difficulty.BEGINNER;
        int count = 5;
        List<Quiz> randomQuizzes = List.of(quiz);

        when(quizRepository.findRandomQuizzes(
                eq(categoryName),
                eq(difficulty.name()),
                eq(count)
        )).thenReturn(randomQuizzes);

        // when
        List<QuizResponseDTO> results = quizService.getRandomQuizzes(categoryName, difficulty, count);

        // then
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
