package com.example.p_project.domain.SignLanguage.service.Impl;

import com.example.p_project.domain.SignLanguage.dto.request.GestureRequestDTO;
import com.example.p_project.domain.SignLanguage.dto.response.GestureResponseDTO;
import com.example.p_project.domain.SignLanguage.entity.GestureRecord;
import com.example.p_project.domain.SignLanguage.repository.GestureRecordRepository;
import com.example.p_project.domain.SignLanguage.service.GestureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 수화 제스처 인식 서비스의 구현 클래스
 * FastAPI 서버와의 통신 및 제스처 데이터 관리를 담당합니다.
 */
@Slf4j  // 로깅을 위한 Lombok 어노테이션
@Service  // 스프링 서비스 컴포넌트 명시
@RequiredArgsConstructor  // final 필드에 대한 생성자 자동 생성
public class GestureServiceImpl implements GestureService {
    private final GestureRecordRepository gestureRecordRepository;  // 제스처 기록 저장소
    private final ObjectMapper objectMapper;  // JSON 변환을 위한 매퍼
    private final WebClient webClient;  // HTTP 클라이언트

    @Value("${fastapi.url}")  // application.properties에서 FastAPI 서버 URL 주입
    private String fastApiUrl;

    /**
     * 제스처를 예측하고 결과를 저장하는 메서드
     * FastAPI 서버에 예측 요청을 보내고 결과를 데이터베이스에 저장합니다.
     *
     * @param request 제스처 예측을 위한 요청 DTO
     * @return 예측 결과를 담은 응답 DTO
     * @throws RuntimeException FastAPI 서버 통신 실패 또는 데이터 처리 실패 시
     */
    @Override
    @Transactional
    public GestureResponseDTO predictGesture(GestureRequestDTO request) {
        try {
            log.info("Sending prediction request to FastAPI server");

            // FastAPI 서버로 요청 전송 및 응답 수신
            GestureResponseDTO response = webClient.post()
                    .uri("/predict")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GestureResponseDTO.class)
                    .block();

            if (response == null) {
                throw new RuntimeException("No response received from FastAPI server");
            }

            // 예측 결과를 데이터베이스에 저장
            GestureRecord record = GestureRecord.builder()
                    .predictedClass(response.getPredictedClass())
                    .confidence(response.getConfidence())
                    .gestureLabel(response.getGestureLabel())
                    .landmarkData(objectMapper.writeValueAsString(request.getLandmarks()))
                    .build();

            gestureRecordRepository.save(record);
            log.info("Prediction result saved successfully: {}", response.getGestureLabel());

            return response;
        } catch (Exception e) {
            log.error("Error during gesture prediction", e);
            throw new RuntimeException("Failed to predict gesture", e);
        }
    }

    /**
     * 최근 예측 결과를 조회하는 메서드
     *
     * @param limit 조회할 결과의 최대 개수
     * @return 최근 예측 결과 목록
     * @throws RuntimeException 데이터 조회 실패 시
     */
    @Override
    @Transactional(readOnly = true)
    public List<GestureResponseDTO> getRecentPredictions(int limit) {
        try {
            return gestureRecordRepository.findTop10ByOrderByCreatedAtDesc()
                    .stream()
                    .limit(limit)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving recent predictions", e);
            throw new RuntimeException("Failed to get recent predictions", e);
        }
    }

    /**
     * 특정 레이블에 해당하는 예측 결과를 조회하는 메서드
     *
     * @param gestureLabel 조회할 제스처 레이블
     * @return 해당 레이블의 예측 결과 목록
     * @throws RuntimeException 데이터 조회 실패 시
     */
    @Override
    @Transactional(readOnly = true)
    public List<GestureResponseDTO> getPredictionsByLabel(String gestureLabel) {
        try {
            return gestureRecordRepository.findByGestureLabel(gestureLabel)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving predictions by label: {}", gestureLabel, e);
            throw new RuntimeException("Failed to get predictions by label", e);
        }
    }

    /**
     * 특정 신뢰도 이상의 예측 결과를 조회하는 메서드
     *
     * @param minConfidence 최소 신뢰도 값
     * @return 지정된 신뢰도 이상의 예측 결과 목록
     * @throws RuntimeException 데이터 조회 실패 시
     */
    @Override
    @Transactional(readOnly = true)
    public List<GestureResponseDTO> getPredictionsByConfidence(double minConfidence) {
        try {
            return gestureRecordRepository.findByConfidenceGreaterThan(minConfidence)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving predictions by confidence: {}", minConfidence, e);
            throw new RuntimeException("Failed to get predictions by confidence", e);
        }
    }

    /**
     * 특정 ID의 예측 결과를 삭제하는 메서드
     *
     * @param id 삭제할 예측 결과의 ID
     * @throws EntityNotFoundException 해당 ID의 결과가 없을 경우
     * @throws RuntimeException 삭제 실패 시
     */
    @Override
    @Transactional
    public void deletePrediction(Long id) {
        try {
            if (!gestureRecordRepository.existsById(id)) {
                throw new EntityNotFoundException("Prediction record not found with id: " + id);
            }
            gestureRecordRepository.deleteById(id);
            log.info("Prediction record deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error deleting prediction record: {}", id, e);
            throw new RuntimeException("Failed to delete prediction record", e);
        }
    }

    /**
     * GestureRecord 엔티티를 GestureResponseDTO로 변환하는 유틸리티 메서드
     *
     * @param record 변환할 GestureRecord 엔티티
     * @return 변환된 GestureResponseDTO 객체
     */
    private GestureResponseDTO convertToDTO(GestureRecord record) {
        GestureResponseDTO dto = new GestureResponseDTO();
        dto.setPredictedClass(record.getPredictedClass());
        dto.setConfidence(record.getConfidence());
        dto.setGestureLabel(record.getGestureLabel());
        return dto;
    }
}
