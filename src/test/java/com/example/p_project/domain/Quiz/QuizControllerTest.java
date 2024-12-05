package com.example.p_project.domain.Quiz;

import com.example.p_project.domain.Quiz.controller.QuizController;
import com.example.p_project.domain.Quiz.dto.request.QuizRequestDTO;
import com.example.p_project.domain.Quiz.dto.response.QuizResponseDTO;
import com.example.p_project.domain.Quiz.entity.Quiz;
import com.example.p_project.domain.Quiz.service.QuizService;
import com.example.p_project.global.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 퀴즈 컨트롤러 테스트 클래스
 * WebMvcTest를 사용하여 컨트롤러 계층만 테스트합니다.
 */
@WebMvcTest(QuizController.class)
@Import(SecurityConfig.class) // Spring Security 설정을 테스트에 포함
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc; // API 엔드포인트 테스트를 위한 MockMvc

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper

    @MockBean
    private QuizService quizService; // 퀴즈 서비스를 모킹하기 위한 MockBean

    // 테스트에 사용될 DTO 객체들
    private QuizRequestDTO quizRequestDTO;
    private QuizResponseDTO quizResponseDTO;

    /**
     * 각 테스트 전에 실행되는 설정 메소드
     * 테스트에 필요한 DTO 객체들을 초기화합니다.
     */
    @BeforeEach
    void setUp() {
        // 요청 DTO 설정
        quizRequestDTO = new QuizRequestDTO();
        quizRequestDTO.setWordId(1L);
        quizRequestDTO.setQuestion("테스트 질문");
        quizRequestDTO.setCorrectAnswer("정답");
        quizRequestDTO.setOption1("오답1");
        quizRequestDTO.setOption2("오답2");
        quizRequestDTO.setOption3("오답3");
        quizRequestDTO.setDifficulty(Quiz.Difficulty.BEGINNER);

        // 응답 DTO 설정
        quizResponseDTO = new QuizResponseDTO();
        quizResponseDTO.setId(1L);
        quizResponseDTO.setQuestion("테스트 질문");
        quizResponseDTO.setCorrectAnswer("정답");
        quizResponseDTO.setOption1("오답1");
        quizResponseDTO.setOption2("오답2");
        quizResponseDTO.setOption3("오답3");
        quizResponseDTO.setDifficulty(Quiz.Difficulty.BEGINNER);
    }

    /**
     * 퀴즈 생성 API 테스트
     * POST /api/quizzes 엔드포인트를 테스트합니다.
     */
    @Test
    @WithMockUser(roles = "USER") // 인증된 사용자로 테스트
    @DisplayName("퀴즈 생성 테스트")
    void createQuizTest() throws Exception {
        // quizService.createQuiz 메소드의 동작을 모킹
        when(quizService.createQuiz(any(QuizRequestDTO.class))).thenReturn(quizResponseDTO);

        // API 요청 실행 및 결과 검증
        mockMvc.perform(post("/api/quizzes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // CSRF 토큰 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizRequestDTO)))
                .andExpect(status().isCreated()) // 201 상태 코드 검증
                .andExpect(jsonPath("$.id").value(1L)) // 응답 데이터 검증
                .andExpect(jsonPath("$.question").value("테스트 질문"));
    }

    /**
     * ID로 퀴즈 조회 API 테스트
     * GET /api/quizzes/{id} 엔드포인트를 테스트합니다.
     */
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("ID로 퀴즈 조회 테스트")
    void getQuizByIdTest() throws Exception {
        // quizService.getQuizById 메소드의 동작을 모킹
        when(quizService.getQuizById(1L)).thenReturn(quizResponseDTO);

        // API 요청 실행 및 결과 검증
        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(status().isOk()) // 200 상태 코드 검증
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("테스트 질문"));
    }

    /**
     * 모든 퀴즈 조회 API 테스트
     * GET /api/quizzes 엔드포인트를 테스트합니다.
     */
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("모든 퀴즈 조회 테스트")
    void getAllQuizzesTest() throws Exception {
        // 여러 퀴즈를 포함하는 리스트 생성
        List<QuizResponseDTO> quizList = Arrays.asList(quizResponseDTO);
        when(quizService.getAllQuizzes()).thenReturn(quizList);

        // API 요청 실행 및 결과 검증
        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("테스트 질문"));
    }

    /**
     * 퀴즈 업데이트 API 테스트
     * PUT /api/quizzes/{id} 엔드포인트를 테스트합니다.
     */
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("퀴즈 업데이트 테스트")
    void updateQuizTest() throws Exception {
        // quizService.updateQuiz 메소드의 동작을 모킹
        when(quizService.updateQuiz(eq(1L), any(QuizRequestDTO.class))).thenReturn(quizResponseDTO);

        // API 요청 실행 및 결과 검증
        mockMvc.perform(put("/api/quizzes/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("테스트 질문"));
    }

    /**
     * 퀴즈 삭제 API 테스트
     * DELETE /api/quizzes/{id} 엔드포인트를 테스트합니다.
     */
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("퀴즈 삭제 테스트")
    void deleteQuizTest() throws Exception {
        // API 요청 실행 및 결과 검증
        mockMvc.perform(delete("/api/quizzes/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent()); // 204 상태 코드 검증
    }

    /**
     * 랜덤 퀴즈 조회 API 테스트
     * GET /api/quizzes/random 엔드포인트를 테스트합니다.
     */
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("랜덤 퀴즈 조회 테스트")
    void getRandomQuizzesTest() throws Exception {
        // 테스트 데이터 설정
        List<QuizResponseDTO> randomQuizzes = Arrays.asList(quizResponseDTO);

        // quizService.getRandomQuizzes 메소드의 동작을 모킹
        when(quizService.getRandomQuizzes(
                anyString(),
                any(Quiz.Difficulty.class),
                anyInt()
        )).thenReturn(randomQuizzes);

        // API 요청 실행 및 결과 검증
        mockMvc.perform(get("/api/quizzes/random")
                        .param("category", "테스트 카테고리")
                        .param("difficulty", "BEGINNER")
                        .param("count", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("테스트 질문"));
    }
}
