package com.example.p_project.domain.Quiz.repository;

import com.example.p_project.domain.Quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByWordId(Long wordId);

    // 카테고리와 난이도로 퀴즈 찾기
    @Query("SELECT q FROM Quiz q JOIN q.word w WHERE w.category.name = :category AND q.difficulty = :difficulty")
    List<Quiz> findByCategoryAndDifficulty(String category, Quiz.Difficulty difficulty);
}
