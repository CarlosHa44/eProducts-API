package com.stmsys.eproducts.services;

import com.stmsys.eproducts.dtos.product.ProductDTO;
import com.stmsys.eproducts.dtos.product.ProductRequestDTO;
import com.stmsys.eproducts.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Optional<Product> getProductById(Long id);
    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> getActiveProducts(Pageable pageable);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);

    // Añadidos para DTO:
    ProductDTO createProduct(ProductRequestDTO dto);
    ProductDTO updateProduct(Long id, ProductRequestDTO dto);

    Optional<ProductDTO> getProductDTOById(Long id);
    Page<ProductDTO> getAllProductsDTO(Pageable pageable);
    Page<ProductDTO> getProductsDTOByCategoryId(Long categoryId, Pageable pageable);
    Page<ProductDTO> getActiveProductsDTO(Pageable pageable);
}