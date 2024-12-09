package com.example.p_project.domain.SignLanguage;

import com.example.p_project.domain.SignLanguage.dto.response.SignLanguageResponseDTO;
import com.example.p_project.domain.SignLanguage.entity.PredictionHistory;
import com.example.p_project.domain.SignLanguage.repository.PredictionHistoryRepository;
import com.example.p_project.domain.SignLanguage.service.Impl.SignLanguageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SignLanguageServiceTest {
    @Mock
    private PredictionHistoryRepository predictionHistoryRepository;

    @InjectMocks
    private SignLanguageServiceImpl signLanguageService;

    private PredictionHistory samplePrediction;
    private MockMultipartFile sampleImage;

    @BeforeEach
    void setUp() {
        samplePrediction = PredictionHistory.builder()
                .id(1L)
                .predictedSign("ㄱ")
                .confidence(0.95)
                .processingTime(100L)
                .imagePath("/path/to/image.jpg")
                .createdAt(LocalDateTime.now())
                .build();

        sampleImage = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );
    }

    @Test
    @DisplayName("최근 예측 기록 조회")
    void getRecentPredictions() {
        // Given
        List<PredictionHistory> predictions = Arrays.asList(samplePrediction);
        given(predictionHistoryRepository.findTop10ByOrderByCreatedAtDesc())
                .willReturn(predictions);

        // When
        List<PredictionHistory> result = signLanguageService.getRecentPredictions();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPredictedSign()).isEqualTo("ㄱ");
        verify(predictionHistoryRepository).findTop10ByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("피드백 업데이트")
    void updateFeedback() {
        // Given
        given(predictionHistoryRepository.findById(1L))
                .willReturn(Optional.of(samplePrediction));

        // When
        signLanguageService.updateFeedback(1L, true, "정확한 예측입니다.");

        // Then
        verify(predictionHistoryRepository).save(any(PredictionHistory.class));
    }

    @Test
    @DisplayName("예측 결과 저장")
    void savePredictionResult() {
        // Given
        SignLanguageResponseDTO responseDTO = SignLanguageResponseDTO.builder()
                .predictedSign("ㄱ")
                .confidence(0.95)
                .processingTime(100L)
                .build();

        given(predictionHistoryRepository.save(any(PredictionHistory.class)))
                .willReturn(samplePrediction);

        // When
        PredictionHistory result = signLanguageService.savePredictionResult(
                responseDTO, "/path/to/image.jpg");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPredictedSign()).isEqualTo("ㄱ");
        verify(predictionHistoryRepository).save(any(PredictionHistory.class));
    }
}
