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

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository, CategoryRepository categoryRepository) {
        this.wordRepository = wordRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public WordResponseDTO createWord(WordRequestDTO wordRequestDTO) {
        // 새로운 Word 엔티티 생성
        Word word = new Word();

        // 기본 정보 설정
        word.setContent(wordRequestDTO.getContent());
        word.setDescription(wordRequestDTO.getDescription());
        word.setVideoUrl(wordRequestDTO.getVideoUrl());

        // Category 설정 - Optional 처리 추가
        Category category = categoryRepository.findById(wordRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        word.setCategory(category);

        // 저장 및 응답 변환
        Word savedWord = wordRepository.save(word);
        return convertToResponseDTO(savedWord);
    }


    @Override
    public WordResponseDTO getWord(Long id) {
        // ID로 단어 조회
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));
        return convertToResponseDTO(word);
    }

    @Override
    public List<WordResponseDTO> getAllWords() {
        // 모든 단어 조회 후 ResponseDTO 리스트로 변환
        return wordRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WordResponseDTO updateWord(Long id, WordRequestDTO wordRequestDTO) {
        // 기존 단어 조회
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        // 단어 정보 업데이트
        word.setContent(wordRequestDTO.getContent());
        word.setDescription(wordRequestDTO.getDescription());
        word.setVideoUrl(wordRequestDTO.getVideoUrl());
        word.setCategory(categoryRepository.findById(wordRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        // 저장 후 ResponseDTO로 변환하여 반환
        Word updatedWord = wordRepository.save(word);
        return convertToResponseDTO(updatedWord);
    }

    @Override
    public void deleteWord(Long id) {
        // ID로 단어 삭제
        wordRepository.deleteById(id);
    }

    @Override
    public List<WordResponseDTO> getWordsByCategory(Long categoryId) {
        // 카테고리 ID로 단어 목록 조회
        return wordRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WordResponseDTO> searchWords(String keyword) {
        // 키워드로 단어 검색
        return wordRepository.findByContentContaining(keyword).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Word 엔티티를 WordResponseDTO로 변환하는 헬퍼 메소드
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
