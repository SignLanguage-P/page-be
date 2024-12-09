package com.example.p_project.domain.SignLanguage;

import com.example.p_project.domain.SignLanguage.controller.SignLanguageController;
import com.example.p_project.domain.SignLanguage.dto.response.SignLanguageResponseDTO;
import com.example.p_project.domain.SignLanguage.service.SignLanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignLanguageController.class)
public class SignLanguageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignLanguageService signLanguageService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("수어 이미지 예측 테스트 - 성공")
    void predictSign_Success() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        SignLanguageResponseDTO responseDTO = SignLanguageResponseDTO.builder()
                .predictedSign("ㄱ")
                .confidence(0.95)
                .processingTime(100L)
                .build();

        given(signLanguageService.predictSign(any())).willReturn(responseDTO);

        // When & Then
        mockMvc.perform(multipart("/api/sign-language/predict")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.predictedSign").value("ㄱ"))
                .andExpect(jsonPath("$.confidence").value(0.95))
                .andExpect(jsonPath("$.processingTime").value(100L));
    }

    @Test
    @DisplayName("잘못된 파일 형식으로 예측 요청 - 실패")
    void predictSign_InvalidFile() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.txt",
                "text/plain",
                "invalid content".getBytes()
        );

        // When & Then
        mockMvc.perform(multipart("/api/sign-language/predict")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("모델 상태 확인")
    void getModelStatus() throws Exception {
        // Given
        given(signLanguageService.getModelStatus()).willReturn("Model is running");

        // When & Then
        mockMvc.perform(get("/api/sign-language/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Model is running"));
    }
}
