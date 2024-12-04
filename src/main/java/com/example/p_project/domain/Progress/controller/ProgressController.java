package com.example.p_project.domain.Progress.controller;

import com.example.p_project.domain.Progress.dto.request.ProgressRequestDTO;
import com.example.p_project.domain.Progress.dto.response.ProgressResponseDTO;
import com.example.p_project.domain.Progress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 진행 상태(Progress) 관련 API를 처리하는 컨트롤러
 * 사용자의 학습 진행 상태에 대한 CRUD 작업을 처리합니다.
 */
@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    private final ProgressService progressService;

    /**
     * ProgressService를 주입받는 생성자
     *
     * @param progressService 진행 상태 관련 비즈니스 로직을 처리하는 서비스
     */
    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    /**
     * 새로운 진행 상태를 생성하거나 기존 진행 상태를 업데이트합니다.
     *
     * @param progressRequestDTO 생성/수정할 진행 상태 정보
     * @return ResponseEntity<ProgressResponseDTO> 생성/수정된 진행 상태 정보와 HTTP 상태 코드 201
     * @throws RuntimeException 요청 처리 중 오류 발생 시
     */
    @PostMapping
    public ResponseEntity<ProgressResponseDTO> createOrUpdateProgress(@RequestBody ProgressRequestDTO progressRequestDTO) {
        ProgressResponseDTO progress = progressService.createOrUpdateProgress(progressRequestDTO);
        return new ResponseEntity<>(progress, HttpStatus.CREATED);
    }

    /**
     * ID로 특정 진행 상태를 조회합니다.
     *
     * @param id 조회할 진행 상태의 고유 식별자
     * @return ResponseEntity<ProgressResponseDTO> 조회된 진행 상태 정보와 HTTP 상태 코드 200
     * @throws RuntimeException 해당 ID의 진행 상태가 존재하지 않는 경우
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgressResponseDTO> getProgress(@PathVariable Long id) {
        ProgressResponseDTO progress = progressService.getProgress(id);
        return ResponseEntity.ok(progress);
    }

    /**
     * 특정 사용자의 모든 진행 상태를 조회합니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @return ResponseEntity<List<ProgressResponseDTO>> 해당 사용자의 모든 진행 상태 목록과 HTTP 상태 코드 200
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressResponseDTO>> getProgressByUser(@PathVariable Long userId) {
        List<ProgressResponseDTO> progress = progressService.getProgressByUser(userId);
        return ResponseEntity.ok(progress);
    }

    /**
     * 특정 사용자의 완료된 진행 상태만 조회합니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @return ResponseEntity<List<ProgressResponseDTO>> 해당 사용자의 완료된 진행 상태 목록과 HTTP 상태 코드 200
     */
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<ProgressResponseDTO>> getCompletedProgressByUser(@PathVariable Long userId) {
        List<ProgressResponseDTO> progress = progressService.getCompletedProgressByUser(userId);
        return ResponseEntity.ok(progress);
    }

    /**
     * 특정 진행 상태를 삭제합니다.
     *
     * @param id 삭제할 진행 상태의 고유 식별자
     * @return ResponseEntity<Void> HTTP 상태 코드 204 (No Content)
     * @throws RuntimeException 해당 ID의 진행 상태가 존재하지 않는 경우
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
