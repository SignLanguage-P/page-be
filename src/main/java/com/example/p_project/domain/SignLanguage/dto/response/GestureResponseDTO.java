package com.example.p_project.domain.SignLanguage.dto.response;

import lombok.Data;

import java.util.List;

/**
 * 수화 제스처 인식 결과를 반환하기 위한 응답 DTO
 * 딥러닝 모델의 예측 결과와 관련 정보를 포함합니다.
 */
@Data  // Lombok 어노테이션: getter, setter, toString, equals, hashCode 메서드 자동 생성
public class GestureResponseDTO {
    /**
     * 예측 처리 상태
     * 가능한 값:
     * - "success": 예측이 정상적으로 완료됨
     * - "error": 예측 중 오류 발생
     * - "no_hand_detected": 손이 감지되지 않음
     */
    private String status;

    /**
     * 예측된 제스처의 클래스 인덱스
     * 모델이 예측한 수화 제스처의 클래스 번호
     * 범위: 0 ~ (전체 클래스 수 - 1)
     */
    private int predictedClass;

    /**
     * 예측에 대한 신뢰도 점수
     * 모델이 예측한 결과에 대한 확신도
     * 범위: 0.0 ~ 1.0
     * - 1.0에 가까울수록 높은 신뢰도
     * - 0.0에 가까울수록 낮은 신뢰도
     */
    private double confidence;

    /**
     * 각 클래스별 예측 확률 리스트
     * 모든 가능한 제스처 클래스에 대한 예측 확률을 포함
     * - 리스트의 각 인덱스는 해당 클래스의 확률을 나타냄
     * - 모든 확률의 합은 1.0
     * - 각 확률 범위: 0.0 ~ 1.0
     */
    private List<Double> probabilities;

    /**
     * 예측된 제스처의 레이블(이름)
     * 예시:
     * - "안녕하세요"
     * - "감사합니다"
     * - "사랑합니다"
     * 등의 실제 수화 의미를 나타내는 문자열
     */
    private String gestureLabel;
}
