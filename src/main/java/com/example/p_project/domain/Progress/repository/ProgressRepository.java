package com.example.p_project.domain.Progress.repository;

import com.example.p_project.domain.Progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Progress 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 작업과 페이징 처리 기능을 제공합니다.
 *
 * @see Progress
 * @see JpaRepository
 */
@Repository  // 스프링 컨테이너가 리포지토리 빈으로 관리하도록 명시
public interface ProgressRepository extends JpaRepository<Progress, Long>{
    /**
     * 특정 사용자의 모든 학습 진행 상태를 조회합니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @return 해당 사용자의 모든 학습 진행 상태 목록
     * @see Progress
     */
    List<Progress> findByUserId(Long userId);

    /**
     * 특정 사용자의 특정 단어에 대한 학습 진행 상태를 조회합니다.
     * 사용자 ID와 단어 ID를 모두 만족하는 단일 진행 상태를 반환합니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @param wordId 조회할 단어의 고유 식별자
     * @return 해당하는 학습 진행 상태 (Optional로 래핑됨)
     * @see Progress
     */
    Optional<Progress> findByUserIdAndWordId(Long userId, Long wordId);

    /**
     * 특정 사용자의 학습 완료 여부에 따른 진행 상태 목록을 조회합니다.
     * 완료된 학습과 진행 중인 학습을 구분하여 조회할 수 있습니다.
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @param completed 학습 완료 여부 (true: 완료, false: 미완료)
     * @return 조건에 맞는 학습 진행 상태 목록
     * @see Progress
     */
    List<Progress> findByUserIdAndCompleted(Long userId, boolean completed);
}
