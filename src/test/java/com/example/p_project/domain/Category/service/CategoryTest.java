package com.example.p_project.domain.Category.service;

import com.example.p_project.domain.Category.entity.Category;
import com.example.p_project.domain.Word.entity.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Category 엔티티에 대한 단위 테스트 클래스
 * Mockito를 사용하여 테스트 환경을 구성합니다.
 */
@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    /**
     * 카테고리 생성 기능을 테스트합니다.
     * 새로운 카테고리가 올바르게 생성되는지 확인합니다.
     */
    @Test
    @DisplayName("카테고리 생성 테스트")
    void createCategoryTest() {
        // given - 테스트에 필요한 데이터 준비
        String name = "테스트 카테고리";
        String description = "테스트 카테고리 설명";

        // when - 테스트하고자 하는 기능 실행
        Category category = Category.builder()
                .name(name)
                .description(description)
                .build();

        // then - 테스트 결과 검증
        // 1. 카테고리 객체가 null이 아닌지 확인
        assertThat(category).isNotNull();
        // 2. 설정한 이름이 정확히 저장되었는지 확인
        assertThat(category.getName()).isEqualTo(name);
        // 3. 설정한 설명이 정확히 저장되었는지 확인
        assertThat(category.getDescription()).isEqualTo(description);
        // 4. 삭제 상태가 기본값(false)인지 확인
        assertThat(category.isDeleted()).isFalse();
        // 5. 단어 리스트가 비어있는지 확인
        assertThat(category.getWords()).isEmpty();
    }

    /**
     * 카테고리 정보 수정 기능을 테스트합니다.
     * 이름과 설명이 정상적으로 업데이트되는지 확인합니다.
     */
    @Test
    @DisplayName("카테고리 정보 수정 테스트")
    void updateCategoryTest() {
        // given - 수정할 카테고리 객체 생성
        Category category = Category.builder()
                .name("원래 이름")
                .description("원래 설명")
                .build();

        String newName = "수정된 이름";
        String newDescription = "수정된 설명";

        // when - 카테고리 정보 수정
        category.update(newName, newDescription);

        // then - 수정된 정보 검증
        assertThat(category.getName()).isEqualTo(newName);
        assertThat(category.getDescription()).isEqualTo(newDescription);
    }

    /**
     * 카테고리에 단어를 추가하는 기능을 테스트합니다.
     * 단어가 정상적으로 추가되고 양방향 관계가 설정되는지 확인합니다.
     */
    @Test
    @DisplayName("카테고리에 단어 추가 테스트")
    void addWordTest() {
        // given - 카테고리와 단어 객체 준비
        Category category = Category.builder()
                .name("테스트 카테고리")
                .description("테스트 설명")
                .build();

        Word word = Word.builder()
                .content("테스트 단어")
                .description("테스트 의미")
                .videoUrl("https://example.com/video.mp4")
                .build();

        // when - 카테고리에 단어 추가
        category.addWord(word);

        // then - 단어 추가 및 연관관계 설정 검증
        assertThat(category.getWords()).hasSize(1);  // 단어 리스트 크기 확인
        assertThat(category.getWords().get(0)).isEqualTo(word);  // 추가된 단어 확인
        assertThat(word.getCategory()).isEqualTo(category);  // 양방향 관계 설정 확인
    }

    /**
     * 카테고리에서 단어를 제거하는 기능을 테스트합니다.
     * 단어가 정상적으로 제거되고 양방향 관계가 해제되는지 확인합니다.
     */
    @Test
    @DisplayName("카테고리에서 단어 제거 테스트")
    void removeWordTest() {
        // given - 카테고리와 단어를 생성하고 연결
        Category category = Category.builder()
                .name("테스트 카테고리")
                .description("테스트 설명")
                .build();

        Word word = Word.builder()
                .content("테스트 단어")
                .description("테스트 의미")
                .videoUrl("https://example.com/video.mp4")
                .build();

        category.addWord(word);

        // when - 카테고리에서 단어 제거
        category.removeWord(word);

        // then - 단어 제거 및 연관관계 해제 검증
        assertThat(category.getWords()).isEmpty();  // 단어 리스트가 비어있는지 확인
        assertThat(word.getCategory()).isNull();  // 단어의 카테고리 참조가 해제되었는지 확인
    }

    /**
     * 카테고리 논리적 삭제 기능을 테스트합니다.
     * isDeleted 플래그가 true로 설정되는지 확인합니다.
     */
    @Test
    @DisplayName("카테고리 논리적 삭제 테스트")
    void deleteCategoryTest() {
        // given - 삭제할 카테고리 준비
        Category category = Category.builder()
                .name("테스트 카테고리")
                .description("테스트 설명")
                .build();

        // when - 카테고리 논리적 삭제 실행
        category.delete();

        // then - 삭제 상태 검증
        assertThat(category.isDeleted()).isTrue();
    }

    /**
     * 카테고리 생성 시 기본값이 올바르게 설정되는지 테스트합니다.
     * isDeleted와 words 컬렉션의 초기 상태를 확인합니다.
     */
    @Test
    @DisplayName("카테고리 생성 시 기본값 설정 테스트")
    void categoryDefaultValuesTest() {
        // given & when - 카테고리 생성
        Category category = Category.builder()
                .name("테스트 카테고리")
                .description("테스트 설명")
                .build();

        // then - 기본값 설정 검증
        assertThat(category.isDeleted()).isFalse();  // 삭제 상태 기본값 확인
        assertThat(category.getWords()).isNotNull();  // words 컬렉션이 초기화되었는지 확인
        assertThat(category.getWords()).isEmpty();  // words 컬렉션이 비어있는지 확인
    }
}
