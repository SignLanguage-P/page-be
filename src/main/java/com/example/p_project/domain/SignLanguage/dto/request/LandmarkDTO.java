package com.example.p_project.domain.SignLanguage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MediaPipe에서 감지한 손의 각 랜드마크 좌표를 표현하는 DTO
 * 3차원 공간에서의 단일 랜드마크 포인트의 위치 정보를 담습니다.
 */
@Data  // getter, setter, toString, equals, hashCode 메서드 자동 생성
@NoArgsConstructor  // 매개변수 없는 기본 생성자 생성
@AllArgsConstructor  // 모든 필드를 매개변수로 받는 생성자 생성
public class LandmarkDTO {
    /**
     * x축 좌표값
     * 화면에서 좌우 위치를 나타냅니다.
     * 범위: 0.0 ~ 1.0 (정규화된 값)
     * - 0.0: 화면의 가장 왼쪽
     * - 1.0: 화면의 가장 오른쪽
     */
    private double x;

    /**
     * y축 좌표값
     * 화면에서 상하 위치를 나타냅니다.
     * 범위: 0.0 ~ 1.0 (정규화된 값)
     * - 0.0: 화면의 가장 위
     * - 1.0: 화면의 가장 아래
     */
    private double y;

    /**
     * z축 좌표값
     * 카메라로부터의 깊이를 나타냅니다.
     * 손목 랜드마크를 기준으로 한 상대적 깊이값
     * - 음수: 카메라 방향으로 더 가까움
     * - 양수: 카메라에서 더 멀어짐
     */
    private double z;
}
