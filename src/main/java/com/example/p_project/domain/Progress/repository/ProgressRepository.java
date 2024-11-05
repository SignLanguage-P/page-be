package com.example.p_project.domain.Progress.repository;

import com.example.p_project.domain.Progress.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long>{
    List<Progress> findByUserId(Long userId);
    Optional<Progress> findByUserIdAndWordId(Long userId, Long wordId);
    List<Progress> findByUserIdAndCompleted(Long userId, boolean completed);
}
