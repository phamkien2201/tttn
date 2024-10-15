package com.tttn.demowebsite.category;

import java.util.List;

public interface ICategoryService {

    void createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(long id);
    List<Category> getAllCategories(int page, int limit);
    void updateCategory(long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(long id);
    List<Category> findCategoryByParentId(Long parentId);

}
