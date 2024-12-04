package com.example.p_project.domain.Word.service.Impl;

import com.example.p_project.domain.Category.entity.Category;
import com.example.p_project.domain.Category.repository.CategoryRepository;
import com.example.p_project.domain.Word.dto.request.WordRequestDTO;
import com.example.p_project.domain.Word.dto.response.WordResponseDTO;
import com.example.p_project.domain.Word.entity.Word;
import com.example.p_project.domain.Word.repository.WordRepository;
import com.example.p_project.domain.Word.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 단어 관련 비즈니스 로직을 구현하는 서비스 클래스입니다.
 * WordService 인터페이스의 구현체로, 단어의 CRUD 작업과 검색 기능을 제공합니다.
 */
@Service
public class WordServiceImpl implements WordService {

    /**
     * 단어 데이터 접근을 위한 레포지토리
     */
    private final WordRepository wordRepository;

    /**
     * 카테고리 데이터 접근을 위한 레포지토리
     */
    private final CategoryRepository categoryRepository;

    /**
     * WordServiceImpl 생성자
     * 필요한 레포지토리들을 주입받습니다.
     *
     * @param wordRepository 단어 레포지토리
     * @param categoryRepository 카테고리 레포지토리
     */
    @Autowired
    public WordServiceImpl(WordRepository wordRepository, CategoryRepository categoryRepository) {
        this.wordRepository = wordRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 새로운 단어를 생성합니다.
     *
     * @param wordRequestDTO 생성할 단어 정보를 담은 DTO
     * @return 생성된 단어의 정보를 담은 ResponseDTO
     * @throws RuntimeException 카테고리를 찾을 수 없는 경우 발생
     */
    @Override
    public WordResponseDTO createWord(WordRequestDTO wordRequestDTO) {
        Word word = new Word();
        word.setContent(wordRequestDTO.getContent());
        word.setDescription(wordRequestDTO.getDescription());
        word.setVideoUrl(wordRequestDTO.getVideoUrl());

        Category category = categoryRepository.findById(wordRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        word.setCategory(category);

        Word savedWord = wordRepository.save(word);
        return convertToResponseDTO(savedWord);
    }

    /**
     * ID로 단어를 조회합니다.
     *
     * @param id 조회할 단어의 ID
     * @return 조회된 단어 정보를 담은 ResponseDTO
     * @throws RuntimeException 단어를 찾을 수 없는 경우 발생
     */
    @Override
    public WordResponseDTO getWord(Long id) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));
        return convertToResponseDTO(word);
    }

    /**
     * 모든 단어를 조회합니다.
     *
     * @return 모든 단어 정보를 담은 ResponseDTO 리스트
     */
    @Override
    public List<WordResponseDTO> getAllWords() {
        return wordRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 기존 단어의 정보를 업데이트합니다.
     *
     * @param id 수정할 단어의 ID
     * @param wordRequestDTO 수정할 단어 정보를 담은 DTO
     * @return 수정된 단어 정보를 담은 ResponseDTO
     * @throws RuntimeException 단어나 카테고리를 찾을 수 없는 경우 발생
     */
    @Override
    public WordResponseDTO updateWord(Long id, WordRequestDTO wordRequestDTO) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        word.setContent(wordRequestDTO.getContent());
        word.setDescription(wordRequestDTO.getDescription());
        word.setVideoUrl(wordRequestDTO.getVideoUrl());
        word.setCategory(categoryRepository.findById(wordRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        Word updatedWord = wordRepository.save(word);
        return convertToResponseDTO(updatedWord);
    }

    /**
     * ID로 단어를 삭제합니다.
     *
     * @param id 삭제할 단어의 ID
     */
    @Override
    public void deleteWord(Long id) {
        wordRepository.deleteById(id);
    }

    /**
     * 특정 카테고리에 속한 단어들을 조회합니다.
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 해당 카테고리의 단어들을 담은 ResponseDTO 리스트
     */
    @Override
    public List<WordResponseDTO> getWordsByCategory(Long categoryId) {
        return wordRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 키워드로 단어를 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @return 검색된 단어들을 담은 ResponseDTO 리스트
     */
    @Override
    public List<WordResponseDTO> searchWords(String keyword) {
        return wordRepository.findByContentContaining(keyword).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Word 엔티티를 WordResponseDTO로 변환합니다.
     *
     * @param word 변환할 Word 엔티티
     * @return 변환된 WordResponseDTO
     */
    private WordResponseDTO convertToResponseDTO(Word word) {
        return new WordResponseDTO(
                word.getId(),
                word.getContent(),
                word.getDescription(),
                word.getVideoUrl(),
                word.getCategory().getName()
        );
    }
}
