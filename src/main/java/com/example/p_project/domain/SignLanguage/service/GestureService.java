package com.example.p_project.domain.SignLanguage.service;

import com.example.p_project.domain.SignLanguage.dto.request.GestureRequestDTO;
import com.example.p_project.domain.SignLanguage.dto.response.GestureResponseDTO;
import java.util.List;

public interface GestureService {
    // 제스처 예측
    GestureResponseDTO predictGesture(GestureRequestDTO request);

    // 최근 예측 기록 조회
    List<GestureResponseDTO> getRecentPredictions(int limit);

    // 특정 제스처 레이블의 예측 기록 조회
    List<GestureResponseDTO> getPredictionsByLabel(String gestureLabel);

    // 특정 신뢰도 이상의 예측 기록 조회
    List<GestureResponseDTO> getPredictionsByConfidence(double minConfidence);

    // 예측 기록 삭제
    void deletePrediction(Long id);
}
