package com.stmsys.eproducts.servicesImp;

import com.stmsys.eproducts.models.entities.Product;
import com.stmsys.eproducts.repositories.ProductRepository;
import com.stmsys.eproducts.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Product> getActiveProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .filter(Product::getActive)
                .map(existing -> {
                    existing.setName(product.getName());
                    existing.setDescription(product.getDescription());
                    existing.setUnitPrice(product.getUnitPrice());
                    existing.setCategory(product.getCategory());
                    existing.setActive(product.getActive());
                    existing.setImageUrl(product.getImageUrl());
                    return productRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .filter(Product::getActive)
                .ifPresent(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                });
    }
}