package com.example.p_project.domain.Category.entity;

import com.example.p_project.domain.Word.entity.Word;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리 정보를 저장하는 엔티티 클래스
 * 데이터베이스의 'categories' 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Word> words = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /**
     * 카테고리 생성을 위한 생성자
     */
    @Builder
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.isDeleted = false;
        this.words = new ArrayList<>();
    }

    /**
     * 카테고리 정보 수정 메서드
     */
    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 단어 추가 메서드
     */
    public void addWord(Word word) {
        this.words.add(word);
        word.setCategory(this);  // 양방향 관계 설정
    }

    /**
     * 단어 제거 메서드
     */
    public void removeWord(Word word) {
        this.words.remove(word);
        word.setCategory(null);
    }

    /**
     * 논리적 삭제 메서드
     */
    public void delete() {
        this.isDeleted = true;
    }
}
