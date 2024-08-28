package com.uh.herts.CartServiceAPI.service;

import com.uh.herts.CartServiceAPI.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);

    Category updateCategory(UUID id, Category category);

    void deleteCategory(UUID id);

    Category getCategoryById(UUID id);

    List<Category> getAllCategories();
}
