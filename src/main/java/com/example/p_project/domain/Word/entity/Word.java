package com.example.p_project.domain.Word.entity;

import com.example.p_project.domain.Category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "words")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;  // 단어 내용

    @Column(nullable = false)
    private String description;  // 단어 설명

    @Column(nullable = false)
    private String videoUrl;  // 수화 동작 비디오 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;  // 단어가 속한 카테고리

    // 생성자, getter, setter 등은 Lombok 어노테이션으로 자동 생성됩니다.
}
