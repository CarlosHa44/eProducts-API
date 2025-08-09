package com.stmsys.eproducts.services;

import com.stmsys.eproducts.models.entities.Order;
import com.stmsys.eproducts.models.entities.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Page<Order> getAllOrders(Pageable pageable);
    Optional<Order> getOrderById(Long id);
    Page<Order> getOrdersByClientId(String clientId, Pageable pageable);
    Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable);
    Page<Order> getOrdersByClientIdAndStatus(String clientId, OrderStatus status, Pageable pageable);
    Order createOrder(Order order);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}
