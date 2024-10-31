package com.example.p_project.domain.Word.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordRequestDTO {
    private String content;  // 단어 내용
    private String description;  // 단어 설명
    private String videoUrl;  // 수화 동작 비디오 URL
    private Long categoryId;  // 카테고리 ID

}