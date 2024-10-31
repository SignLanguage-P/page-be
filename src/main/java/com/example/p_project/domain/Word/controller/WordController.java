package com.example.p_project.domain.Word.controller;

import com.example.p_project.domain.Word.dto.request.WordRequestDTO;
import com.example.p_project.domain.Word.dto.response.WordResponseDTO;
import com.example.p_project.domain.Word.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<WordResponseDTO> createWord(@RequestBody WordRequestDTO wordRequestDTO) {
        WordResponseDTO createdWord = wordService.createWord(wordRequestDTO);
        return new ResponseEntity<>(createdWord, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WordResponseDTO> getWord(@PathVariable Long id) {
        WordResponseDTO word = wordService.getWord(id);
        return ResponseEntity.ok(word);
    }

    @GetMapping
    public ResponseEntity<List<WordResponseDTO>> getAllWords() {
        List<WordResponseDTO> words = wordService.getAllWords();
        return ResponseEntity.ok(words);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WordResponseDTO> updateWord(@PathVariable Long id, @RequestBody WordRequestDTO wordRequestDTO) {
        WordResponseDTO updatedWord = wordService.updateWord(id, wordRequestDTO);
        return ResponseEntity.ok(updatedWord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<WordResponseDTO>> getWordsByCategory(@PathVariable Long categoryId) {
        List<WordResponseDTO> words = wordService.getWordsByCategory(categoryId);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/search")
    public ResponseEntity<List<WordResponseDTO>> searchWords(@RequestParam String keyword) {
        List<WordResponseDTO> words = wordService.searchWords(keyword);
        return ResponseEntity.ok(words);
    }
}
}
