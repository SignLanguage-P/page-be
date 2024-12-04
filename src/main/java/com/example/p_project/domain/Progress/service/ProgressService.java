package com.example.p_project.domain.Progress.service;

import com.example.p_project.domain.Progress.dto.request.ProgressRequestDTO;
import com.example.p_project.domain.Progress.dto.response.ProgressResponseDTO;

import java.util.List;
/**
 * 학습 진행 상태 관리를 위한 서비스 인터페이스
 * 사용자의 단어 학습 진행 상태에 대한 비즈니스 로직을 정의합니다.
 */
public interface ProgressService {
    /**
     * 학습 진행 상태를 생성하거나 업데이트합니다.
     * 동일한 사용자-단어 조합이 이미 존재하는 경우 업데이트하고,
     * 존재하지 않는 경우 새로운 진행 상태를 생성합니다.
     *
     * @param progressRequestDTO 생성/업데이트할 학습 진행 정보가 담긴 DTO
     * @return 생성/업데이트된 학습 진행 정보 (ProgressResponseDTO)
     * @throws RuntimeException 사용자나 단어를 찾을 수 없는 경우
     */
    ProgressResponseDTO createOrUpdateProgress(ProgressRequestDTO progressRequestDTO);

    /**
     * 특정 ID의 학습 진행 상태를 조회합니다.
     *
     * @param id 조회할 학습 진행 상태의 고유 식별자
     * @return 조회된 학습 진행 정보 (ProgressResponseDTO)
     * @throws RuntimeException 해당 ID의 학습 진행 상태가 존재하지 않는 경우
     */
    ProgressResponseDTO getProgress(Long id);

    /**
     * 특정 사용자의 모든 학습 진행 상태를 조회합니다.
     * 완료 여부와 관계없이 모든 학습 진행 상태를 반환합니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @return 해당 사용자의 모든 학습 진행 상태 목록
     */
    List<ProgressResponseDTO> getProgressByUser(Long userId);

    /**
     * 특정 사용자의 완료된 학습 진행 상태만 조회합니다.
     * 학습 완료로 표시된 항목들만 필터링하여 반환합니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @return 해당 사용자의 완료된 학습 진행 상태 목록
     */
    List<ProgressResponseDTO> getCompletedProgressByUser(Long userId);

    /**
     * 특정 학습 진행 상태를 삭제합니다.
     *
     * @param id 삭제할 학습 진행 상태의 고유 식별자
     * @throws RuntimeException 해당 ID의 학습 진행 상태가 존재하지 않는 경우
     */
    void deleteProgress(Long id);
}
