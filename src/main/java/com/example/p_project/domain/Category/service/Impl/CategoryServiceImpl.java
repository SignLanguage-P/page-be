package com.example.p_project.domain.Category.service.Impl;

import com.example.p_project.domain.Category.dto.request.CategoryRequestDTO;
import com.example.p_project.domain.Category.dto.response.CategoryResponseDTO;
import com.example.p_project.domain.Category.entity.Category;
import com.example.p_project.domain.Category.repository.CategoryRepository;
import com.example.p_project.domain.Category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 서비스의 구현 클래스
 * 카테고리와 관련된 비즈니스 로직을 처리합니다.
 */
@Service  // 스프링의 서비스 컴포넌트임을 명시
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * CategoryRepository를 주입받는 생성자
     *
     * @param categoryRepository 카테고리 데이터 조작을 위한 리포지토리
     */
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * 새로운 카테고리를 생성합니다.
     *
     * @param categoryRequestDTO 생성할 카테고리 정보를 담은 DTO
     * @return 생성된 카테고리 정보를 담은 ResponseDTO
     */
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());

        Category savedCategory = categoryRepository.save(category);
        return convertToResponseDTO(savedCategory);
    }

    /**
     * ID로 카테고리를 조회합니다.
     *
     * @param id 조회할 카테고리의 ID
     * @return 조회된 카테고리 정보를 담은 ResponseDTO
     * @throws RuntimeException 카테고리를 찾을 수 없는 경우
     */
    @Override
    public CategoryResponseDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToResponseDTO(category);
    }

    /**
     * 이름으로 카테고리를 조회합니다.
     *
     * @param name 조회할 카테고리의 이름
     * @return 조회된 카테고리 정보를 담은 ResponseDTO
     * @throws RuntimeException 카테고리를 찾을 수 없는 경우
     */
    @Override
    public CategoryResponseDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToResponseDTO(category);
    }

    /**
     * 모든 카테고리를 조회합니다.
     *
     * @return 모든 카테고리 정보를 담은 ResponseDTO 리스트
     */
    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 기존 카테고리 정보를 업데이트합니다.
     *
     * @param id 수정할 카테고리의 ID
     * @param categoryRequestDTO 수정할 카테고리 정보를 담은 DTO
     * @return 수정된 카테고리 정보를 담은 ResponseDTO
     * @throws RuntimeException 카테고리를 찾을 수 없는 경우
     */
    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    /**
     * 카테고리를 삭제합니다.
     *
     * @param id 삭제할 카테고리의 ID
     */
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Category 엔티티를 CategoryResponseDTO로 변환합니다.
     *
     * @param category 변환할 Category 엔티티
     * @return 변환된 CategoryResponseDTO
     */
    private CategoryResponseDTO convertToResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
