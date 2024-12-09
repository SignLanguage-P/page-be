package com.example.p_project.domain.SignLanguage.repository;

import com.example.p_project.domain.SignLanguage.entity.PredictionHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PredictionHistoryRepository extends JpaRepository<PredictionHistory, Long> {
    List<PredictionHistory> findTop10ByOrderByCreatedAtDesc();

    List<PredictionHistory> findByPredictedSignOrderByCreatedAtDesc(String predictedSign);

    List<PredictionHistory> findByConfidenceGreaterThanEqual(Double confidence);

    @Query("SELECT p FROM PredictionHistory p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<PredictionHistory> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT COUNT(p) FROM PredictionHistory p WHERE p.isCorrect = true")
    Long countCorrectPredictions();
}
