package com.stmsys.eproducts.repositories;

import com.stmsys.eproducts.models.entities.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    Page<OrderDetail> findByOrderId(Long orderId, Pageable pageable);
    Page<OrderDetail> findByProductId(Long productId, Pageable pageable);
}
