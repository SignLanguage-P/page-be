package com.example.p_project.domain.SignLanguage;

import ai.djl.Model;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.TranslatorContext;
import com.example.p_project.domain.SignLanguage.translator.MyImageTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class MyImageTranslatorTest {
    @Mock
    private TranslatorContext context;

    @Mock
    private NDManager ndManager;

    @Mock
    private Model model;

    private MyImageTranslator translator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        translator = new MyImageTranslator(224, 224);
        when(context.getNDManager()).thenReturn(ndManager);
        when(context.getModel()).thenReturn(model);
    }

    @Test
    @DisplayName("Batchifier 설정 확인")
    void getBatchifier() {
        assertThat(translator.getBatchifier()).isNotNull();
    }

    @Test
    @DisplayName("기본 클래스 목록 확인")
    void defaultClassesExist() throws IOException {
        // Given
        when(model.getProperty("synset")).thenReturn(null); // synset 파일이 없는 경우를 시뮬레이션

        // When
        translator.prepare(context);

        // Then
        assertThat(translator).hasFieldOrPropertyWithValue("width", 224);
        assertThat(translator).hasFieldOrPropertyWithValue("height", 224);
    }

    @Test
    @DisplayName("Translator 초기화 확인")
    void translatorInitialization() {
        // Given & When
        MyImageTranslator translator = new MyImageTranslator(224, 224);

        // Then
        assertThat(translator).isNotNull();
        assertThat(translator).hasFieldOrPropertyWithValue("width", 224);
        assertThat(translator).hasFieldOrPropertyWithValue("height", 224);
    }

    @Test
    @DisplayName("synset 파일이 없을 때 기본 클래스 사용 확인")
    void useDefaultClassesWhenSynsetNotAvailable() throws IOException {
        // Given
        when(model.getProperty("synset")).thenReturn(null);

        // When
        translator.prepare(context);

        // Then
        assertThat(translator).extracting("synset")
                .asList()
                .isNotEmpty()
                .hasSize(24); // 기본 클래스 개수 (한글 자음 14개 + 모음 10개)
    }
}
