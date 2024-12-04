package com.example.p_project.domain.Category.service;

import com.example.p_project.domain.Category.dto.request.CategoryRequestDTO;
import com.example.p_project.domain.Category.dto.response.CategoryResponseDTO;

import java.util.List;
/**
 * 카테고리 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 * 카테고리의 생성, 조회, 수정, 삭제 등의 기능을 제공합니다.
 */
public interface CategoryService {
    /**
     * 새로운 카테고리를 생성합니다.
     *
     * @param categoryRequestDTO 생성할 카테고리 정보가 담긴 DTO
     * @return CategoryResponseDTO 생성된 카테고리 정보
     */
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    /**
     * ID를 통해 특정 카테고리를 조회합니다.
     *
     * @param id 조회할 카테고리의 고유 식별자
     * @return CategoryResponseDTO 조회된 카테고리 정보
     * @throws RuntimeException 해당 ID의 카테고리가 존재하지 않는 경우
     */
    CategoryResponseDTO getCategory(Long id);

    /**
     * 이름을 통해 특정 카테고리를 조회합니다.
     *
     * @param name 조회할 카테고리의 이름
     * @return CategoryResponseDTO 조회된 카테고리 정보
     * @throws RuntimeException 해당 이름의 카테고리가 존재하지 않는 경우
     */
    CategoryResponseDTO getCategoryByName(String name);

    /**
     * 모든 카테고리 목록을 조회합니다.
     *
     * @return List<CategoryResponseDTO> 전체 카테고리 목록
     */
    List<CategoryResponseDTO> getAllCategories();

    /**
     * 기존 카테고리 정보를 업데이트합니다.
     *
     * @param id 수정할 카테고리의 고유 식별자
     * @param categoryRequestDTO 수정할 카테고리 정보가 담긴 DTO
     * @return CategoryResponseDTO 수정된 카테고리 정보
     * @throws RuntimeException 해당 ID의 카테고리가 존재하지 않는 경우
     */
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    /**
     * 특정 카테고리를 삭제합니다.
     *
     * @param id 삭제할 카테고리의 고유 식별자
     * @throws RuntimeException 해당 ID의 카테고리가 존재하지 않는 경우
     */
    void deleteCategory(Long id);
}
