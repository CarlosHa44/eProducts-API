package com.stmsys.eproducts.servicesImp;

import com.stmsys.eproducts.models.entities.Order;
import com.stmsys.eproducts.models.entities.OrderStatus;
import com.stmsys.eproducts.repositories.OrderRepository;
import com.stmsys.eproducts.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findByActiveTrue(pageable);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id)
                .filter(Order::getActive);
    }

    @Override
    public Page<Order> getOrdersByClientId(String clientId, Pageable pageable) {
        return orderRepository.findByClientIdAndActiveTrue(clientId, pageable);
    }

    @Override
    public Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatusAndActiveTrue(status, pageable);
    }

    @Override
    public Page<Order> getOrdersByClientIdAndStatus(String clientId, OrderStatus status, Pageable pageable) {
        return orderRepository.findByClientIdAndStatusAndActiveTrue(clientId, status, pageable);
    }

    @Override
    public Order createOrder(Order order) {
        order.setActive(true);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
                .filter(Order::getActive)
                .map(existing -> {
                    existing.setClientId(order.getClientId());
                    existing.setShippingAddress(order.getShippingAddress());
                    existing.setCreatedAt(order.getCreatedAt());
                    existing.setStatus(order.getStatus());
                    existing.setTotal(order.getTotal());
                    existing.setActive(order.getActive());
                    return orderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id)
                .filter(Order::getActive)
                .ifPresent(order -> {
                    order.setActive(false);
                    orderRepository.save(order);
                });
    }
}
