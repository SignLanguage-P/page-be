package com.example.p_project.domain.SignLanguage.repository;

import com.example.p_project.domain.SignLanguage.entity.GestureRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 수화 제스처 기록에 대한 데이터베이스 접근을 담당하는 리포지토리
 * JpaRepository를 상속받아 기본적인 CRUD 작업과 페이징 기능을 제공합니다.
 *
 * @param *GestureRecord 엔티티 타입
 * @param *Long 기본키 타입
 */
@Repository  // 스프링의 리포지토리 컴포넌트임을 명시
public interface GestureRecordRepository extends JpaRepository<GestureRecord, Long> {
    /**
     * 가장 최근에 생성된 10개의 제스처 기록을 조회합니다.
     * 생성 시간(createdAt)을 기준으로 내림차순 정렬하여 상위 10개를 반환합니다.
     *
     * @return 최근 10개의 GestureRecord 리스트
     * 예시 쿼리: SELECT * FROM gesture_records ORDER BY created_at DESC LIMIT 10
     */
    List<GestureRecord> findTop10ByOrderByCreatedAtDesc();

    /**
     * 특정 제스처 레이블에 해당하는 모든 기록을 조회합니다.
     *
     * @param gestureLabel 검색할 제스처 레이블 (예: "안녕하세요", "감사합니다" 등)
     * @return 해당 레이블을 가진 모든 GestureRecord 리스트
     * 예시 쿼리: SELECT * FROM gesture_records WHERE gesture_label = ?
     */
    List<GestureRecord> findByGestureLabel(String gestureLabel);

    /**
     * 지정된 신뢰도보다 높은 신뢰도를 가진 모든 제스처 기록을 조회합니다.
     *
     * @param confidence 기준이 되는 최소 신뢰도 값 (0.0 ~ 1.0 범위)
     * @return 지정된 신뢰도보다 높은 모든 GestureRecord 리스트
     * 예시 쿼리: SELECT * FROM gesture_records WHERE confidence > ?
     */
    List<GestureRecord> findByConfidenceGreaterThan(double confidence);
}
