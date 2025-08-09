package com.stmsys.eproducts.repositories;

import com.stmsys.eproducts.models.entities.Order;
import com.stmsys.eproducts.models.entities.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders for a specific client
    Page<Order> findByActiveTrue(Pageable pageable);
    Page<Order> findByClientIdAndActiveTrue(String clientId, Pageable pageable);
    Page<Order> findByStatusAndActiveTrue(OrderStatus status, Pageable pageable);
    Page<Order> findByClientIdAndStatusAndActiveTrue(String clientId, OrderStatus status, Pageable pageable);
}
