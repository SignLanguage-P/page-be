package com.example.p_project.domain.SignLanguage.dto.request;

import lombok.Data;

import java.util.List;

/**
 * 수화 제스처 인식을 위한 요청 DTO (Data Transfer Object)
 * MediaPipe에서 추출한 손 랜드마크 데이터를 전송하기 위한 객체입니다.
 */
@Data  // Lombok 어노테이션: getter, setter, toString, equals, hashCode 메서드 자동 생성
public class GestureRequestDTO {
    /**
     * 손 랜드마크 좌표 데이터를 담는 2차원 리스트
     *
     * 구조:
     * - 외부 List: 감지된 손의 수 (일반적으로 1개 또는 2개의 손)
     * - 내부 List: 각 손마다 21개의 랜드마크 포인트
     *
     * 예시:
     * [
     *   [ // 첫 번째 손
     *     {x: 0.1, y: 0.2, z: 0.3}, // 랜드마크 0
     *     {x: 0.2, y: 0.3, z: 0.4}, // 랜드마크 1
     *     ... // 총 21개의 랜드마크
     *   ],
     *   [ // 두 번째 손 (있는 경우)
     *     ...
     *   ]
     * ]
     */
    private List<List<LandmarkDTO>> landmarks;
}
