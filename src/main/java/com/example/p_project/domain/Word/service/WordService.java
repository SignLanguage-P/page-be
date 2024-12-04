package com.example.p_project.domain.Word.service;

import com.example.p_project.domain.Word.dto.request.WordRequestDTO;
import com.example.p_project.domain.Word.dto.response.WordResponseDTO;

import java.util.List;
/**
 * 단어 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * 단어의 생성, 조회, 수정, 삭제 및 검색 기능을 제공합니다.
 */
public interface WordService {
    /**
     * 새로운 단어를 생성합니다.
     *
     * @param wordRequestDTO 생성할 단어의 정보를 담은 DTO
     * @return 생성된 단어의 정보를 담은 WordResponseDTO
     * @throws RuntimeException 카테고리가 존재하지 않는 경우 발생
     */
    WordResponseDTO createWord(WordRequestDTO wordRequestDTO);

    /**
     * ID를 통해 특정 단어를 조회합니다.
     *
     * @param id 조회할 단어의 고유 식별자
     * @return 조회된 단어의 정보를 담은 WordResponseDTO
     * @throws RuntimeException 해당 ID의 단어가 존재하지 않는 경우 발생
     */
    WordResponseDTO getWord(Long id);

    /**
     * 모든 단어 목록을 조회합니다.
     *
     * @return 모든 단어 정보를 담은 WordResponseDTO 리스트
     */
    List<WordResponseDTO> getAllWords();

    /**
     * 기존 단어의 정보를 업데이트합니다.
     *
     * @param id 수정할 단어의 고유 식별자
     * @param wordRequestDTO 수정할 단어의 새로운 정보를 담은 DTO
     * @return 수정된 단어의 정보를 담은 WordResponseDTO
     * @throws RuntimeException 해당 ID의 단어나 카테고리가 존재하지 않는 경우 발생
     */
    WordResponseDTO updateWord(Long id, WordRequestDTO wordRequestDTO);

    /**
     * 특정 단어를 삭제합니다.
     *
     * @param id 삭제할 단어의 고유 식별자
     * @throws RuntimeException 해당 ID의 단어가 존재하지 않는 경우 발생
     */
    void deleteWord(Long id);

    /**
     * 특정 카테고리에 속한 모든 단어를 조회합니다.
     *
     * @param categoryId 조회할 카테고리의 고유 식별자
     * @return 해당 카테고리에 속한 모든 단어 정보를 담은 WordResponseDTO 리스트
     */
    List<WordResponseDTO> getWordsByCategory(Long categoryId);

    /**
     * 키워드를 포함하는 단어들을 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @return 검색 결과에 해당하는 단어들의 정보를 담은 WordResponseDTO 리스트
     */
    List<WordResponseDTO> searchWords(String keyword);
}
