package com.example.p_project.domain.Word.service;

import com.example.p_project.domain.Word.dto.request.WordRequestDTO;
import com.example.p_project.domain.Word.dto.response.WordResponseDTO;

import java.util.List;

public interface WordService {
    WordResponseDTO createWord(WordRequestDTO wordRequestDTO);

    WordResponseDTO getWord(Long id);

    List<WordResponseDTO> getAllWords();

    WordResponseDTO updateWord(Long id, WordRequestDTO wordRequestDTO);

    void deleteWord(Long id);

    List<WordResponseDTO> getWordsByCategory(Long categoryId);

    List<WordResponseDTO> searchWords(String keyword);
}
