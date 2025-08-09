package com.stmsys.eproducts.repositories;

import com.stmsys.eproducts.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByActiveTrue(Pageable pageable);
}
