package com.stmsys.eproducts.servicesImp;

import com.stmsys.eproducts.dtos.category.CategoryDTO;
import com.stmsys.eproducts.dtos.product.ProductDTO;
import com.stmsys.eproducts.dtos.product.ProductRequestDTO;
import com.stmsys.eproducts.models.entities.Category;
import com.stmsys.eproducts.models.entities.Product;
import com.stmsys.eproducts.repositories.CategoryRepository;
import com.stmsys.eproducts.repositories.ProductRepository;
import com.stmsys.eproducts.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    public Page<ProductDTO> getAllProductsDTO(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable).map(this::toDTO);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findByIdAndActiveTrue(id);
    }

    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.findByIdAndActiveTrue(id).map(this::toDTO);
    }

    @Override
    public Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
    }

    public Page<ProductDTO> getProductsDTOByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable).map(this::toDTO);
    }

    @Override
    public Page<Product> getActiveProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    public Page<ProductDTO> getActiveProductsDTO(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable).map(this::toDTO);
    }

    @Override
    public Product createProduct(Product product) {
        product.setActive(true);
        return productRepository.save(product);
    }

    public ProductDTO createProduct(ProductRequestDTO dto) {
        Category category = categoryRepository.findByIdAndActiveTrue(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found or inactive"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setUnitPrice(dto.getUnitPrice());
        product.setCategory(category);
        product.setImageUrl(dto.getImageUrl());
        product.setActive(dto.getActive() != null ? dto.getActive() : true);

        return toDTO(productRepository.save(product));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRepository.findByIdAndActiveTrue(id).map(existing -> {
            existing.setName(product.getName());
            existing.setDescription(product.getDescription());
            existing.setUnitPrice(product.getUnitPrice());
            existing.setCategory(product.getCategory());
            existing.setImageUrl(product.getImageUrl());
            existing.setActive(product.getActive());
            return productRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Product not found or inactive"));
    }

    public ProductDTO updateProduct(Long id, ProductRequestDTO dto) {
        return productRepository.findByIdAndActiveTrue(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setUnitPrice(dto.getUnitPrice());
            existing.setImageUrl(dto.getImageUrl());
            if (dto.getActive() != null) {
                existing.setActive(dto.getActive());
            }

            if (dto.getCategoryId() != null) {
                Category category = categoryRepository.findByIdAndActiveTrue(dto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found or inactive"));
                existing.setCategory(category);
            }

            return toDTO(productRepository.save(existing));
        }).orElseThrow(() -> new RuntimeException("Product not found or inactive"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findByIdAndActiveTrue(id).ifPresent(product -> {
            product.setActive(false);
            productRepository.save(product);
        });
    }

    // ---------------------- Mappers ----------------------
    private ProductDTO toDTO(Product product) {
        CategoryDTO categoryDTO = null;
        if (product.getCategory() != null && Boolean.TRUE.equals(product.getCategory().getActive())) {
            categoryDTO = new CategoryDTO(
                    product.getCategory().getId(),
                    product.getCategory().getName(),
                    product.getCategory().getDescription(),
                    product.getCategory().getActive(),
                    null
            );
        }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getImageUrl(),
                product.getActive(),
                categoryDTO
        );
    }
}