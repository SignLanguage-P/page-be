package com.example.p_project.domain.Word.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 단어 정보를 클라이언트에게 응답할 때 사용하는 DTO(Data Transfer Object) 클래스입니다.
 * 이 클래스는 단어의 상세 정보를 포함하며, API 응답에 사용됩니다.
 */
@Getter // 모든 필드의 getter 메서드를 자동 생성
@Setter // 모든 필드의 setter 메서드를 자동 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 자동 생성
public class WordResponseDTO {
    /**
     * 단어의 고유 식별자입니다.
     * 데이터베이스에서 자동 생성되는 ID 값입니다.
     */
    private Long id;

    /**
     * 단어의 내용입니다.
     * 실제 단어나 문구가 저장됩니다.
     */
    private String content;

    /**
     * 단어에 대한 설명입니다.
     * 단어의 의미나 용례 등 상세 설명이 포함됩니다.
     */
    private String description;

    /**
     * 단어와 관련된 영상의 URL입니다.
     * 수화 동작이나 관련 교육 영상의 링크가 저장됩니다.
     */
    private String videoUrl;

    /**
     * 단어가 속한 카테고리의 이름입니다.
     * 단어의 분류나 주제를 나타냅니다.
     */
    private String categoryName;
}
