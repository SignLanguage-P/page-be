package com.example.p_project.domain.Category.repository;

import com.example.p_project.domain.Category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 카테고리 엔티티에 대한 데이터베이스 조작을 담당하는 리포지토리 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 작업과 페이징 처리 기능을 제공받습니다.
 *
 * @see Category 카테고리 엔티티
 * @see JpaRepository JPA 리포지토리 인터페이스
 */
@Repository  // 스프링의 Repository 컴포넌트임을 명시
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * 카테고리 이름으로 카테고리를 조회하는 메서드
     *
     * @param name 검색할 카테고리 이름
     * @return Optional<Category> 조회된 카테고리를 Optional로 감싸서 반환
     *         카테고리가 존재하지 않을 경우 Optional.empty() 반환
     */
    Optional<Category> findByName(String name);

// JpaRepository로부터 상속받는 주요 메서드들:
// - save(S entity): 엔티티 저장 및 수정
// - delete(T entity): 엔티티 삭제
// - findById(ID id): ID로 엔티티 조회
// - findAll(): 모든 엔티티 조회
// - count(): 엔티티 개수 조회
}
