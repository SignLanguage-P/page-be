package com.example.p_project.domain.SignLanguage.controller;

import com.example.p_project.domain.SignLanguage.dto.request.SignLanguageRequestDTO;
import com.example.p_project.domain.SignLanguage.dto.response.SignLanguageResponseDTO;
import com.example.p_project.domain.SignLanguage.entity.PredictionHistory;
import com.example.p_project.domain.SignLanguage.service.SignLanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sign-language")
@RequiredArgsConstructor
@Slf4j
public class SignLanguageController {
    private final SignLanguageService signLanguageService;

    // 1. 수어 이미지 예측 엔드포인트
    @PostMapping("/predict")
    public ResponseEntity<SignLanguageResponseDTO> predictSign(
            @RequestParam("image") MultipartFile image) {
        // 1. 입력값 검증
        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(SignLanguageResponseDTO.error("이미지 파일이 필요합니다."));
        }

        // 2. 파일 형식 검증
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest()
                    .body(SignLanguageResponseDTO.error("유효한 이미지 파일이 아닙니다."));
        }

        try {
            // 3. 서비스 호출 및 예측 수행
            SignLanguageResponseDTO result = signLanguageService.predictSign(image);

            // 4. 서비스 결과 검증
            if (result == null) {
                return ResponseEntity.internalServerError()
                        .body(SignLanguageResponseDTO.error("예측을 처리할 수 없습니다."));
            }

            // 5. 서비스에서 반환된 에러 처리
            if (result.getErrorMessage() != null) {
                return ResponseEntity.badRequest().body(result);
            }

            // 6. 정상 응답
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 7. 예외 처리
            log.error("수어 인식 처리 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body(SignLanguageResponseDTO.error("서버 내부 오류가 발생했습니다."));
        }
    }



    // 2. 모델 상태 확인 엔드포인트
    @GetMapping("/status")
    public ResponseEntity<String> getModelStatus() {
        return ResponseEntity.ok(signLanguageService.getModelStatus());
    }

    // 3. 최근 예측 기록 조회 엔드포인트
    @GetMapping("/history")
    public ResponseEntity<List<PredictionHistory>> getRecentPredictions() {
        return ResponseEntity.ok(signLanguageService.getRecentPredictions());
    }

    // 4. 피드백 제공 엔드포인트
    @PostMapping("/feedback/{predictionId}")
    public ResponseEntity<Void> provideFeedback(
            @PathVariable Long predictionId,
            @RequestParam boolean isCorrect,
            @RequestParam(required = false) String feedback) {
        signLanguageService.updateFeedback(predictionId, isCorrect, feedback);
        return ResponseEntity.ok().build();
    }

    // 5. 통계 정보 조회 엔드포인트
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(signLanguageService.getPredictionStatistics());
    }
}
