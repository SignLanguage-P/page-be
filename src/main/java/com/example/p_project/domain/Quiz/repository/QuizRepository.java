package com.example.p_project.domain.Quiz.repository;

import com.example.p_project.domain.Quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 퀴즈 엔티티에 대한 데이터베이스 조작을 담당하는 리포지토리 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 제공받습니다.
 *
 * @see Quiz
 * @see JpaRepository
 */
@Repository  // 스프링의 리포지토리 컴포넌트임을 명시
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    /**
     * 특정 단어 ID에 해당하는 모든 퀴즈를 조회합니다.
     * Spring Data JPA의 메서드 이름 규칙을 따라 자동으로 쿼리가 생성됩니다.
     *
     * @param wordId 조회할 단어의 ID
     * @return 해당 단어와 연관된 퀴즈 목록
     * @see Quiz #word
     */
    List<Quiz> findByWordId(Long wordId);

    /**
     * 특정 카테고리와 난이도에 해당하는 퀴즈들을 조회합니다.
     * JPQL을 사용하여 직접 쿼리를 정의합니다.
     *
     * 쿼리 설명:
     * - Quiz 엔티티(q)와 연관된 Word 엔티티(w)를 조인
     * - Word의 카테고리 이름과 Quiz의 난이도를 기준으로 필터링
     *
     * @param category 조회할 카테고리 이름 (예: "동물", "과일" 등)
     * @param difficulty 조회할 퀴즈 난이도 (BEGINNER, INTERMEDIATE, ADVANCED)
     * @return 조건에 맞는 퀴즈 목록
     * @see Quiz.Difficulty
     */
    @Query("SELECT q FROM Quiz q JOIN q.word w WHERE w.category.name = :category AND q.difficulty = :difficulty")
    List<Quiz> findByCategoryAndDifficulty(String category, Quiz.Difficulty difficulty);
}
