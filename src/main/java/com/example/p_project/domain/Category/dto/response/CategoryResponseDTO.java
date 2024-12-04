package com.example.p_project.domain.Category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 조회 결과를 반환하기 위한 DTO(Data Transfer Object) 클래스
 * 클라이언트에게 카테고리 정보를 전달하는 응답 객체로 사용됩니다.
 */
@Getter             // Lombok: 모든 필드의 Getter 메서드 자동 생성
@Setter             // Lombok: 모든 필드의 Setter 메서드 자동 생성
@NoArgsConstructor  // Lombok: 매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor // Lombok: 모든 필드를 매개변수로 받는 생성자 자동 생성
public class CategoryResponseDTO {
    /**
     * 카테고리 고유 식별자
     * 데이터베이스에서 자동 생성되는 Primary Key 값
     */
    private Long id;

    /**
     * 카테고리 이름
     * 카테고리를 구분하는 고유한 이름 정보
     */
    private String name;

    /**
     * 카테고리 설명
     * 해당 카테고리에 대한 상세 설명 정보
     */
    private String description;
}
