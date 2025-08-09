package com.stmsys.eproducts.repositories;

import com.stmsys.eproducts.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    // Buscar un producto activo por su ID
    Optional<Product> findByIdAndActiveTrue(Long id);

    // Buscar productos activos por categoría
    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);
}
