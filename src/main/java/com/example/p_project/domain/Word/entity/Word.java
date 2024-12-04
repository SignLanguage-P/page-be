package com.example.p_project.domain.Word.entity;

import com.example.p_project.domain.Category.entity.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "words")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;  // 단어 내용

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;  // 단어 설명

    @Column(name = "video_url", nullable = false)
    private String videoUrl;  // 수화 동작 비디오 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;  // 단어가 속한 카테고리

    // 조회수
    @Column(name = "view_count", columnDefinition = "bigint default 0")
    private Long viewCount = 0L;

    // 생성 시간
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 시간
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 삭제 여부
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /**
     * 단어 생성을 위한 정적 팩토리 메서드
     */
    @Builder
    public Word(String content, String description, String videoUrl, Category category) {
        this.content = content;
        this.description = description;
        this.videoUrl = videoUrl;
        this.category = category;
        this.viewCount = 0L;
        this.isDeleted = false;
    }

    /**
     * 카테고리 설정 메서드
     * 양방향 관계 설정을 위한 메서드
     */
    public void setCategory(Category category) {
        // 기존 카테고리와의 관계 제거
        if (this.category != null) {
            this.category.getWords().remove(this);
        }
        this.category = category;
        // 새로운 카테고리와의 관계 설정
        if (category != null && !category.getWords().contains(this)) {
            category.getWords().add(this);
        }
    }

    /**
     * 단어 정보 수정 메서드
     */
    public void update(String content, String description, String videoUrl) {
        this.content = content;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    /**
     * 조회수 증가 메서드
     */
    public void incrementViewCount() {
        this.viewCount++;
    }

    /**
     * 논리적 삭제 메서드
     */
    public void delete() {
        this.isDeleted = true;
    }

    /**
     * 비디오 URL 유효성 검사 메서드
     */
    private void validateVideoUrl(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("비디오 URL은 필수 입력값입니다.");
        }
        // URL 형식 검증 로직 추가 가능
    }
}
