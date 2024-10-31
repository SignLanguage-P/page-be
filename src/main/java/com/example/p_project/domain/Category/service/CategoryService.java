package com.example.p_project.domain.Category.service;

import com.example.p_project.domain.Category.dto.request.CategoryRequestDTO;
import com.example.p_project.domain.Category.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO getCategory(Long id);
    CategoryResponseDTO getCategoryByName(String name);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);
    void deleteCategory(Long id);
}
