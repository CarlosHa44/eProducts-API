package com.stmsys.eproducts.servicesImp;

import com.stmsys.eproducts.models.entities.Category;
import com.stmsys.eproducts.repositories.CategoryRepository;
import com.stmsys.eproducts.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        return categoryRepository.findByIdAndActiveTrue(id)
                .map(existing -> {
                    existing.setName(category.getName());
                    existing.setDescription(category.getDescription());
                    return categoryRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findByIdAndActiveTrue(id)
                .ifPresent(category -> {
                    category.setActive(false);
                    categoryRepository.save(category);
                });
    }
}