package com.example.p_project.domain.Category.controller;

import com.example.p_project.domain.Category.dto.request.CategoryRequestDTO;
import com.example.p_project.domain.Category.dto.response.CategoryResponseDTO;
import com.example.p_project.domain.Category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    /** CategoryService 인스턴스 */
    private final CategoryService categoryService;

    /**
     * CategoryController 생성자
     * @param categoryService 카테고리 서비스 구현체
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 새로운 카테고리를 생성합니다.
     * HTTP Method: POST
     * @param categoryRequestDTO 카테고리 생성 요청 데이터
     * @return 생성된 카테고리 정보와 함께 201(CREATED) 응답
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * ID로 카테고리를 조회합니다.
     * HTTP Method: GET
     * @param id 조회할 카테고리 ID
     * @return 조회된 카테고리 정보와 함께 200(OK) 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    /**
     * 이름으로 카테고리를 조회합니다.
     * HTTP Method: GET
     * @param name 조회할 카테고리 이름
     * @return 조회된 카테고리 정보와 함께 200(OK) 응답
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(@PathVariable String name) {
        CategoryResponseDTO category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    /**
     * 모든 카테고리를 조회합니다.
     * HTTP Method: GET
     * @return 전체 카테고리 목록과 함께 200(OK) 응답
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * 기존 카테고리 정보를 수정합니다.
     * HTTP Method: PUT
     * @param id 수정할 카테고리 ID
     * @param categoryRequestDTO 수정할 카테고리 정보
     * @return 수정된 카테고리 정보와 함께 200(OK) 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * 카테고리를 삭제합니다.
     * HTTP Method: DELETE
     * @param id 삭제할 카테고리 ID
     * @return 204(NO_CONTENT) 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
