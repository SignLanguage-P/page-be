package com.example.p_project.domain.Quiz.repository;

import com.example.p_project.domain.Quiz.entity.Quiz;
import org.apache.ibatis.annotations.Param;
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
     *
     * @param wordId 조회할 단어의 ID
     * @return 해당 단어와 연관된 퀴즈 목록
     */
    List<Quiz> findByWordId(Long wordId);

    /**
     * 특정 카테고리와 난이도에 해당하는 퀴즈들을 조회합니다.
     *
     * @param category 조회할 카테고리 이름
     * @param difficulty 조회할 퀴즈 난이도
     * @return 조건에 맞는 퀴즈 목록
     */
    @Query("SELECT q FROM Quiz q JOIN FETCH q.word w WHERE w.category.name = :category AND q.difficulty = :difficulty")
    List<Quiz> findByCategoryAndDifficulty(
            @Param("category") String category,
            @Param("difficulty") Quiz.Difficulty difficulty
    );

    /**
     * 특정 카테고리와 난이도에 해당하는 퀴즈들을 랜덤하게 조회합니다.
     *
     * @param category 조회할 카테고리 이름
     * @param difficulty 조회할 퀴즈 난이도
     * @param limit 조회할 퀴즈 개수
     * @return 조건에 맞는 랜덤 퀴즈 목록
     */
//    @Query(value = "SELECT q.* FROM quizzes q " +
//            "INNER JOIN words w ON q.word_id = w.id " +
//            "INNER JOIN categories c ON w.category_id = c.id " +
//            "WHERE c.name = :category AND q.difficulty = :difficulty " +
//            "ORDER BY RANDOM() " +
//            "LIMIT :limit",
//            nativeQuery = true)
//    List<Quiz> findRandomQuizzes(
//            @Param("category") String category,
//            @Param("difficulty") String difficulty,
//            @Param("limit") int limit
//    );

    /**
     * 특정 난이도에 해당하는 모든 퀴즈를 조회합니다.
     *
     * @param difficulty 조회할 퀴즈 난이도
     * @return 해당 난이도의 퀴즈 목록
     */
    List<Quiz> findByDifficulty(Quiz.Difficulty difficulty);

    /**
     * 특정 카테고리에 속한 모든 퀴즈를 조회합니다.
     *
     * @param categoryName 조회할 카테고리 이름
     * @return 해당 카테고리의 퀴즈 목록
     */
    @Query("SELECT q FROM Quiz q JOIN q.word w WHERE w.category.name = :categoryName")
    List<Quiz> findByCategory(@Param("categoryName") String categoryName);
}
