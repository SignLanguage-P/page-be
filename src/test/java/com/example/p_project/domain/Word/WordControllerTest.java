package com.example.p_project.domain.Word;

import com.example.p_project.domain.Category.entity.Category;
import com.example.p_project.domain.Category.repository.CategoryRepository;
import com.example.p_project.domain.Word.dto.request.WordRequestDTO;
import com.example.p_project.domain.Word.dto.response.WordResponseDTO;
import com.example.p_project.domain.Word.entity.Word;
import com.example.p_project.domain.Word.repository.WordRepository;
import com.example.p_project.domain.Word.service.Impl.WordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * WordService 구현체에 대한 단위 테스트 클래스입니다.
 * JUnit5와 Mockito를 사용하여 테스트를 수행합니다.
 */
@ExtendWith(MockitoExtension.class)
public class WordControllerTest {
    @Mock
    private WordRepository wordRepository;  // 단어 레포지토리 모의 객체

    @Mock
    private CategoryRepository categoryRepository;  // 카테고리 레포지토리 모의 객체

    @InjectMocks
    private WordServiceImpl wordService;  // 테스트 대상 서비스

    // 테스트에 사용될 객체들
    private Category category;
    private Word word;
    private WordRequestDTO wordRequestDTO;
    private final Long WORD_ID = 1L;
    private final Long CATEGORY_ID = 1L;

    /**
     * 각 테스트 실행 전에 수행되는 설정 메소드입니다.
     * 테스트에 필요한 기본 데이터를 초기화합니다.
     */
    @BeforeEach
    void setUp() {
        // 테스트용 카테고리 객체 생성
        category = Category.builder()
                .id(CATEGORY_ID)
                .name("테스트 카테고리")
                .description("테스트용 카테고리입니다.")
                .build();

        // 테스트용 단어 객체 생성
        word = Word.builder()
                .content("테스트 단어")
                .description("테스트 단어 설명")
                .videoUrl("https://example.com/test.mp4")
                .category(category)
                .build();

        // 테스트용 요청 DTO 생성
        wordRequestDTO = new WordRequestDTO();
        wordRequestDTO.setContent("테스트 단어");
        wordRequestDTO.setDescription("테스트 단어 설명");
        wordRequestDTO.setVideoUrl("https://example.com/test.mp4");
        wordRequestDTO.setCategoryId(CATEGORY_ID);
    }

    /**
     * 단어 생성 기능을 테스트합니다.
     * 새로운 단어가 정상적으로 생성되는지 확인합니다.
     */
    @Test
    @DisplayName("단어 생성 테스트")
    void createWordTest() {
        // given: 테스트 환경 설정
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(wordRepository.save(any(Word.class))).thenReturn(word);

        // when: 테스트 실행
        WordResponseDTO result = wordService.createWord(wordRequestDTO);

        // then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(wordRequestDTO.getContent());
        assertThat(result.getDescription()).isEqualTo(wordRequestDTO.getDescription());
        assertThat(result.getVideoUrl()).isEqualTo(wordRequestDTO.getVideoUrl());
        verify(wordRepository, times(1)).save(any(Word.class));
    }

    /**
     * 존재하지 않는 카테고리로 단어 생성 시 예외 발생을 테스트합니다.
     */
    @Test
    @DisplayName("존재하지 않는 카테고리로 단어 생성 시 예외 발생 테스트")
    void createWordWithInvalidCategoryTest() {
        // given: 존재하지 않는 카테고리 설정
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

        // when & then: 예외 발생 검증
        assertThatThrownBy(() -> wordService.createWord(wordRequestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Category not found");
    }

    /**
     * ID로 단어 조회 기능을 테스트합니다.
     */
    @Test
    @DisplayName("ID로 단어 조회 테스트")
    void getWordByIdTest() {
        // given: 테스트 데이터 설정
        when(wordRepository.findById(WORD_ID)).thenReturn(Optional.of(word));

        // when: 테스트 실행
        WordResponseDTO result = wordService.getWord(WORD_ID);

        // then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(word.getContent());
        verify(wordRepository, times(1)).findById(WORD_ID);
    }

    /**
     * 모든 단어 조회 기능을 테스트합니다.
     */
    @Test
    @DisplayName("모든 단어 조회 테스트")
    void getAllWordsTest() {
        // given
        List<Word> words = List.of(word);
        when(wordRepository.findAll()).thenReturn(words);

        // when
        List<WordResponseDTO> results = wordService.getAllWords();

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isNotNull();  // null check 추가
        assertThat(results.get(0).getContent()).isEqualTo(word.getContent());
        verify(wordRepository).findAll();
    }

    /**
     * 단어 업데이트 기능을 테스트합니다.
     */
    @Test
    @DisplayName("단어 업데이트 테스트")
    void updateWordTest() {
        // given: 테스트 데이터 설정
        when(wordRepository.findById(WORD_ID)).thenReturn(Optional.of(word));
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(wordRepository.save(any(Word.class))).thenReturn(word);

        // when: 테스트 실행
        WordResponseDTO result = wordService.updateWord(WORD_ID, wordRequestDTO);

        // then: 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(wordRequestDTO.getContent());
        verify(wordRepository).save(any(Word.class));
    }

    /**
     * 단어 삭제 기능을 테스트합니다.
     */
    @Test
    @DisplayName("단어 삭제 테스트")
    void deleteWordTest() {
        // given: 테스트 데이터 설정
        doNothing().when(wordRepository).deleteById(WORD_ID);

        // when: 테스트 실행
        wordService.deleteWord(WORD_ID);

        // then: 결과 검증
        verify(wordRepository, times(1)).deleteById(WORD_ID);
    }

    /**
     * 카테고리별 단어 조회 기능을 테스트합니다.
     */
    @Test
    @DisplayName("카테고리별 단어 조회 테스트")
    void getWordsByCategoryTest() {
        // given: 테스트 데이터 설정
        List<Word> categoryWords = List.of(word);
        when(wordRepository.findByCategoryId(CATEGORY_ID)).thenReturn(categoryWords);

        // when: 테스트 실행
        List<WordResponseDTO> results = wordService.getWordsByCategory(CATEGORY_ID);

        // then: 결과 검증
        assertThat(results)
                .isNotNull()
                .hasSize(1);
        assertThat(results.get(0).getContent()).isEqualTo(word.getContent());
        verify(wordRepository).findByCategoryId(CATEGORY_ID);
    }

    /**
     * 키워드로 단어 검색 기능을 테스트합니다.
     */
    @Test
    @DisplayName("단어 검색 테스트")
    void searchWordsTest() {
        // given: 테스트 데이터 설정
        String keyword = "테스트";
        List<Word> searchResults = List.of(word);
        when(wordRepository.findByContentContaining(keyword)).thenReturn(searchResults);

        // when: 테스트 실행
        List<WordResponseDTO> results = wordService.searchWords(keyword);

        // then: 결과 검증
        assertThat(results)
                .isNotNull()
                .hasSize(1);
        assertThat(results.get(0).getContent()).isEqualTo(word.getContent());
        verify(wordRepository).findByContentContaining(keyword);
    }
}
