package com.stmsys.eproducts.controllers;
import com.stmsys.eproducts.dtos.product.ProductDTO;
import com.stmsys.eproducts.dtos.product.ProductRequestDTO;
import com.stmsys.eproducts.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable)
                .map(ProductDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ProductDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductDTO> getProductsByCategoryId(@PathVariable Long categoryId, Pageable pageable) {
        return productService.getProductsByCategoryId(categoryId, pageable)
                .map(ProductDTO::fromEntity);
    }

    @GetMapping("/active")
    public Page<ProductDTO> getActiveProducts(Pageable pageable) {
        return productService.getActiveProducts(pageable)
                .map(ProductDTO::fromEntity);
    }

    // Cambiado para recibir ProductRequestDTO en lugar de Product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequestDTO dto) {
        ProductDTO savedProduct = productService.createProduct(dto);
        return ResponseEntity
                .created(URI.create("/api/products/" + savedProduct.getId()))
                .body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        ProductDTO updatedProduct = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}