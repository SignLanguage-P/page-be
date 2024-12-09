package com.example.p_project.domain.SignLanguage.service;

import com.example.p_project.domain.SignLanguage.dto.response.SignLanguageResponseDTO;
import com.example.p_project.domain.SignLanguage.entity.PredictionHistory;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SignLanguageService {
    // 이미지로부터 수어를 예측하는 메서드
    SignLanguageResponseDTO predictSign(MultipartFile file);

    // 모델의 현재 상태를 확인하는 메서드
    String getModelStatus();

    // 최근 10개의 예측 기록을 조회하는 메서드
    List<PredictionHistory> getRecentPredictions();

    // 특정 수어에 대한 예측 기록을 조회하는 메서드
    List<PredictionHistory> getPredictionsBySign(String sign);

    // 특정 신뢰도 이상의 예측 기록을 조회하는 메서드
    List<PredictionHistory> getPredictionsByConfidence(Double minConfidence);

    // 특정 기간 동안의 예측 기록을 조회하는 메서드
    List<PredictionHistory> getPredictionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // 예측 결과를 저장하는 메서드
    PredictionHistory savePredictionResult(SignLanguageResponseDTO result, String imagePath);

    // 예측 결과에 대한 피드백을 업데이트하는 메서드
    void updateFeedback(Long predictionId, boolean isCorrect, String feedback);

    // 예측 통계 정보를 조회하는 메서드
    Map<String, Object> getPredictionStatistics();
}
