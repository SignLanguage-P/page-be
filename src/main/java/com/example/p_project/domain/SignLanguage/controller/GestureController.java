package com.example.p_project.domain.SignLanguage.controller;

import com.example.p_project.domain.SignLanguage.dto.request.GestureRequestDTO;
import com.example.p_project.domain.SignLanguage.dto.response.GestureResponseDTO;
import com.example.p_project.domain.SignLanguage.service.GestureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 수화 제스처 인식을 위한 컨트롤러
 * 클라이언트로부터 제스처 데이터를 받아 처리하고 예측 결과를 반환합니다.
 */
@RestController  // REST API 컨트롤러임을 명시
@RequestMapping("/api/gesture")  // 기본 URL 경로 설정
@RequiredArgsConstructor  // final 필드에 대한 생성자 자동 생성
public class GestureController {
    /**
     * 제스처 서비스 의존성 주입
     * RequiredArgsConstructor 어노테이션으로 인해 자동으로 주입됩니다.
     */
    private final GestureService gestureService;

    /**
     * 제스처 예측 엔드포인트
     * 클라이언트로부터 받은 제스처 데이터를 분석하여 예측 결과를 반환합니다.
     *
     * @param request 제스처 데이터를 포함한 요청 DTO
     * @return ResponseEntity<GestureResponseDTO> 예측 결과를 포함한 응답
     *
     * HTTP Method: POST
     * URL: /api/gesture/predict
     * Content-Type: application/json
     */
    @PostMapping("/predict")
    public ResponseEntity<GestureResponseDTO> predictGesture(@RequestBody GestureRequestDTO request) {
        // 서비스 계층에 제스처 예측 요청
        GestureResponseDTO response = gestureService.predictGesture(request);
        // 예측 결과를 200 OK 상태코드와 함께 반환
        return ResponseEntity.ok(response);
    }
}
