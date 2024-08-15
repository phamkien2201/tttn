package com.tttn.demowebsite.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .parentId(categoryDTO.getParentId())
                .build();
        categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);
        return categoryPage.getContent();
    }

    @Override
    public void updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setId(categoryDTO.getParentId());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(long id) {
        //xoa cung
       categoryRepository.deleteById(id);
    }
}
