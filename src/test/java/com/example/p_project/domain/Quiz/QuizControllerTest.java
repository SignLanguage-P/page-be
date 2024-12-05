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

@WebMvcTest(QuizController.class)
@Import(SecurityConfig.class) // 보안 설정 import
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuizService quizService;

    private QuizRequestDTO quizRequestDTO;
    private QuizResponseDTO quizResponseDTO;

    @BeforeEach
    void setUp() {
        // 기존 setUp 코드 유지
        quizRequestDTO = new QuizRequestDTO();
        quizRequestDTO.setWordId(1L);
        quizRequestDTO.setQuestion("테스트 질문");
        quizRequestDTO.setCorrectAnswer("정답");
        quizRequestDTO.setOption1("오답1");
        quizRequestDTO.setOption2("오답2");
        quizRequestDTO.setOption3("오답3");
        quizRequestDTO.setDifficulty(Quiz.Difficulty.BEGINNER);

        quizResponseDTO = new QuizResponseDTO();
        quizResponseDTO.setId(1L);
        quizResponseDTO.setQuestion("테스트 질문");
        quizResponseDTO.setCorrectAnswer("정답");
        quizResponseDTO.setOption1("오답1");
        quizResponseDTO.setOption2("오답2");
        quizResponseDTO.setOption3("오답3");
        quizResponseDTO.setDifficulty(Quiz.Difficulty.BEGINNER);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("퀴즈 생성 테스트")
    void createQuizTest() throws Exception {
        when(quizService.createQuiz(any(QuizRequestDTO.class))).thenReturn(quizResponseDTO);

        mockMvc.perform(post("/api/quizzes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("테스트 질문"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("ID로 퀴즈 조회 테스트")
    void getQuizByIdTest() throws Exception {
        when(quizService.getQuizById(1L)).thenReturn(quizResponseDTO);

        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("테스트 질문"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("모든 퀴즈 조회 테스트")
    void getAllQuizzesTest() throws Exception {
        List<QuizResponseDTO> quizList = Arrays.asList(quizResponseDTO);
        when(quizService.getAllQuizzes()).thenReturn(quizList);

        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("테스트 질문"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("퀴즈 업데이트 테스트")
    void updateQuizTest() throws Exception {
        when(quizService.updateQuiz(eq(1L), any(QuizRequestDTO.class))).thenReturn(quizResponseDTO);

        mockMvc.perform(put("/api/quizzes/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("테스트 질문"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("퀴즈 삭제 테스트")
    void deleteQuizTest() throws Exception {
        mockMvc.perform(delete("/api/quizzes/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("랜덤 퀴즈 조회 테스트")
    void getRandomQuizzesTest() throws Exception {
        // 테스트 데이터 준비
        List<QuizResponseDTO> randomQuizzes = Arrays.asList(quizResponseDTO);

        // Mockito 설정 수정
        when(quizService.getRandomQuizzes(
                anyString(),
                any(Quiz.Difficulty.class),
                anyInt()
        )).thenReturn(randomQuizzes);

        mockMvc.perform(get("/api/quizzes/random")
                        .param("category", "테스트 카테고리")
                        .param("difficulty", "BEGINNER")
                        .param("count", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("테스트 질문"));
    }
}
