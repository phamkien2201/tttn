package com.tttn.demowebsite.category;

import com.tttn.demowebsite.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ICategoryService iCategoryService;

    @PostMapping("")
    @Operation(summary = "Tạo danh mục sản phẩm")
    public void createCategories(
            @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
    }

    //hien thi all category
    @GetMapping("")//http://localhost:8080/api/v1/categories?page=1&limit=10
    @Operation(summary = "Lấy tất cả danh mục sản phâm")
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false ) Integer limit
    ) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 150;
        }
        List<Category> categories = categoryService.getAllCategories(page, limit);
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Cập nhat danh muc")
    public void updateCategories(
            @PathVariable Long id,
           @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa danh mục")
    public void deleteCategories(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Tìm danh mục bằng parentId")
    public ResponseEntity<List<Category>> findCategoriesByParentId(@PathVariable Long parentId) {
        List<Category> categories = categoryService.findCategoryByParentId(parentId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy danh muc bằng id")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

}
