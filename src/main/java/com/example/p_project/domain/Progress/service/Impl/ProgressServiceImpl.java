package com.example.p_project.domain.Progress.service.Impl;

import com.example.p_project.domain.Progress.dto.request.ProgressRequestDTO;
import com.example.p_project.domain.Progress.dto.response.ProgressResponseDTO;
import com.example.p_project.domain.Progress.entity.Progress;
import com.example.p_project.domain.Progress.repository.ProgressRepository;
import com.example.p_project.domain.Progress.service.ProgressService;
import com.example.p_project.domain.User.repository.UserRepository;
import com.example.p_project.domain.Word.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 학습 진행 상태 관리를 위한 서비스 구현 클래스
 * ProgressService 인터페이스의 구현체로, 학습 진행 상태와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;

    /**
     * 생성자를 통한 의존성 주입
     *
     * @param progressRepository 학습 진행 상태 리포지토리
     * @param userRepository 사용자 리포지토리
     * @param wordRepository 단어 리포지토리
     */
    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository, UserRepository userRepository, WordRepository wordRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
    }

    /**
     * 학습 진행 상태를 생성하거나 업데이트합니다.
     * 이미 존재하는 사용자-단어 조합의 경우 업데이트하고, 없는 경우 새로 생성합니다.
     *
     * @param progressRequestDTO 생성/업데이트할 학습 진행 정보를 담은 DTO
     * @return 생성/업데이트된 학습 진행 정보
     * @throws RuntimeException 사용자나 단어를 찾을 수 없는 경우
     */
    @Override
    public ProgressResponseDTO createOrUpdateProgress(ProgressRequestDTO progressRequestDTO) {
        Progress progress = progressRepository.findByUserIdAndWordId(progressRequestDTO.getUserId(), progressRequestDTO.getWordId())
                .orElse(new Progress());

        progress.setUser(userRepository.findById(progressRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        progress.setWord(wordRepository.findById(progressRequestDTO.getWordId())
                .orElseThrow(() -> new RuntimeException("Word not found")));
        progress.setCompleted(progressRequestDTO.isCompleted());
        progress.setLastStudiedAt(LocalDateTime.now());
        progress.setStudyCount(progress.getStudyCount() + 1);

        Progress savedProgress = progressRepository.save(progress);
        return convertToResponseDTO(savedProgress);
    }

    /**
     * 특정 ID의 학습 진행 상태를 조회합니다.
     *
     * @param id 조회할 학습 진행 상태의 ID
     * @return 조회된 학습 진행 정보
     * @throws RuntimeException 해당 ID의 학습 진행 상태를 찾을 수 없는 경우
     */
    @Override
    public ProgressResponseDTO getProgress(Long id) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        return convertToResponseDTO(progress);
    }

    /**
     * 특정 사용자의 모든 학습 진행 상태를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 해당 사용자의 모든 학습 진행 상태 목록
     */
    @Override
    public List<ProgressResponseDTO> getProgressByUser(Long userId) {
        return progressRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 완료된 학습 진행 상태만 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 해당 사용자의 완료된 학습 진행 상태 목록
     */
    @Override
    public List<ProgressResponseDTO> getCompletedProgressByUser(Long userId) {
        return progressRepository.findByUserIdAndCompleted(userId, true).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 특정 학습 진행 상태를 삭제합니다.
     *
     * @param id 삭제할 학습 진행 상태의 ID
     */
    @Override
    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }

    /**
     * Progress 엔티티를 ProgressResponseDTO로 변환합니다.
     * 엔티티의 정보를 클라이언트에게 전달하기 위한 DTO 형태로 변환하는 내부 메서드입니다.
     *
     * @param progress 변환할 Progress 엔티티
     * @return 변환된 ProgressResponseDTO 객체
     */
    private ProgressResponseDTO convertToResponseDTO(Progress progress) {
        return new ProgressResponseDTO(
                progress.getId(),
                progress.getUser().getId(),
                progress.getUser().getUsername(),
                progress.getWord().getId(),
                progress.getWord().getContent(),
                progress.isCompleted(),
                progress.getLastStudiedAt(),
                progress.getStudyCount()
        );
    }
}
