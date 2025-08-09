package com.stmsys.eproducts.servicesImp;
import com.stmsys.eproducts.dtos.category.CategoryDTO;
import com.stmsys.eproducts.dtos.category.CategoryRequestDTO;
import com.stmsys.eproducts.dtos.product.ProductDTO;
import com.stmsys.eproducts.models.entities.Category;
import com.stmsys.eproducts.repositories.CategoryRepository;
import com.stmsys.eproducts.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findByActiveTrue();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findByIdAndActiveTrue(id);
    }

    public List<CategoryDTO> getAllCategoriesDTO() {
        return categoryRepository.findByActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryDTOById(Long id) {
        return categoryRepository.findByIdAndActiveTrue(id)
                .map(this::toDTO);
    }

    @Override
    public Category createCategory(Category category) {
        category.setActive(true);
        return categoryRepository.save(category);
    }

    public CategoryDTO createCategory(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setActive(dto.getActive() != null ? dto.getActive() : true);
        return toDTO(categoryRepository.save(category));
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        return categoryRepository.findByIdAndActiveTrue(id).map(existing -> {
            existing.setName(category.getName());
            existing.setDescription(category.getDescription());
            existing.setActive(category.getActive());
            return categoryRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Category not found or inactive"));
    }

    public CategoryDTO updateCategory(Long id, CategoryRequestDTO dto) {
        return categoryRepository.findByIdAndActiveTrue(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            if (dto.getActive() != null) {
                existing.setActive(dto.getActive());
            }
            return toDTO(categoryRepository.save(existing));
        }).orElseThrow(() -> new RuntimeException("Category not found or inactive"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findByIdAndActiveTrue(id).ifPresent(category -> {
            category.setActive(false);
            categoryRepository.save(category);
        });
    }

    // ---------------------- Mappers ----------------------
    private CategoryDTO toDTO(Category category) {
        List<ProductDTO> products = category.getProducts() != null
                ? category.getProducts().stream()
                .filter(Product -> Boolean.TRUE.equals(Product.getActive()))
                .map(p -> new ProductDTO(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getUnitPrice(),
                        p.getImageUrl(),
                        p.getActive(),
                        null
                ))
                .collect(Collectors.toList())
                : null;

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive(),
                products
        );
    }
}