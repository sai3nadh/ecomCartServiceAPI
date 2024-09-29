package com.uh.herts.CartServiceAPI.service.impl;

import com.uh.herts.CartServiceAPI.entity.Category;
import com.uh.herts.CartServiceAPI.repository.CategoryRepository;
import com.uh.herts.CartServiceAPI.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(Instant.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID id, Category category) {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {

    }

    @Override
    public Category getCategoryById(UUID id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
