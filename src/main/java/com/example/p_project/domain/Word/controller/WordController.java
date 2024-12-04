package com.example.p_project.domain.Word.controller;

import com.example.p_project.domain.Word.dto.request.WordRequestDTO;
import com.example.p_project.domain.Word.dto.response.WordResponseDTO;
import com.example.p_project.domain.Word.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 단어 관련 API를 처리하는 REST 컨트롤러입니다.
 * 기본 경로: /api/words
 */
@RestController
@RequestMapping("/api/words")
public class WordController {

    /**
     * 단어 관련 비즈니스 로직을 처리하는 서비스
     */
    private final WordService wordService;

    /**
     * WordController 생성자
     * @param wordService 주입될 WordService 구현체
     */
    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * 새로운 단어를 생성합니다.
     * HTTP Method: POST
     * 경로: /api/words
     *
     * @param wordRequestDTO 생성할 단어 정보를 담은 DTO
     * @return 생성된 단어 정보와 함께 201 Created 상태 코드 반환
     */
    @PostMapping
    public ResponseEntity<WordResponseDTO> createWord(@RequestBody WordRequestDTO wordRequestDTO) {
        WordResponseDTO createdWord = wordService.createWord(wordRequestDTO);
        return new ResponseEntity<>(createdWord, HttpStatus.CREATED);
    }

    /**
     * ID로 특정 단어를 조회합니다.
     * HTTP Method: GET
     * 경로: /api/words/{id}
     *
     * @param id 조회할 단어의 ID
     * @return 조회된 단어 정보와 함께 200 OK 상태 코드 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<WordResponseDTO> getWord(@PathVariable Long id) {
        WordResponseDTO word = wordService.getWord(id);
        return ResponseEntity.ok(word);
    }

    /**
     * 모든 단어를 조회합니다.
     * HTTP Method: GET
     * 경로: /api/words
     *
     * @return 모든 단어 목록과 함께 200 OK 상태 코드 반환
     */
    @GetMapping
    public ResponseEntity<List<WordResponseDTO>> getAllWords() {
        List<WordResponseDTO> words = wordService.getAllWords();
        return ResponseEntity.ok(words);
    }

    /**
     * 특정 단어의 정보를 수정합니다.
     * HTTP Method: PUT
     * 경로: /api/words/{id}
     *
     * @param id 수정할 단어의 ID
     * @param wordRequestDTO 수정할 단어 정보를 담은 DTO
     * @return 수정된 단어 정보와 함께 200 OK 상태 코드 반환
     */
    @PutMapping("/{id}")
    public ResponseEntity<WordResponseDTO> updateWord(@PathVariable Long id, @RequestBody WordRequestDTO wordRequestDTO) {
        WordResponseDTO updatedWord = wordService.updateWord(id, wordRequestDTO);
        return ResponseEntity.ok(updatedWord);
    }

    /**
     * 특정 단어를 삭제합니다.
     * HTTP Method: DELETE
     * 경로: /api/words/{id}
     *
     * @param id 삭제할 단어의 ID
     * @return 204 No Content 상태 코드 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 특정 카테고리에 속한 단어들을 조회합니다.
     * HTTP Method: GET
     * 경로: /api/words/category/{categoryId}
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 해당 카테고리의 단어 목록과 함께 200 OK 상태 코드 반환
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<WordResponseDTO>> getWordsByCategory(@PathVariable Long categoryId) {
        List<WordResponseDTO> words = wordService.getWordsByCategory(categoryId);
        return ResponseEntity.ok(words);
    }

    /**
     * 키워드로 단어를 검색합니다.
     * HTTP Method: GET
     * 경로: /api/words/search?keyword={keyword}
     *
     * @param keyword 검색할 키워드
     * @return 검색된 단어 목록과 함께 200 OK 상태 코드 반환
     */
    @GetMapping("/search")
    public ResponseEntity<List<WordResponseDTO>> searchWords(@RequestParam String keyword) {
        List<WordResponseDTO> words = wordService.searchWords(keyword);
        return ResponseEntity.ok(words);
    }
}

