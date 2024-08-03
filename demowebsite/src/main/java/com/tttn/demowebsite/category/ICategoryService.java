package com.tttn.demowebsite.category;

import java.util.List;

public interface ICategoryService {

    void createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    void updateCategory(long categoryId, CategoryDTO categoryDTO);

    void deleteCategory(long id);
}
