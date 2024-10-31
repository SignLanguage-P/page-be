package com.example.p_project.domain.Word.repository;

import com.example.p_project.domain.Word.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    // 카테고리 ID로 단어 목록 조회
    List<Word> findByCategoryId(Long categoryId);

    // 단어 내용으로 검색
    List<Word> findByContentContaining(String content);
}