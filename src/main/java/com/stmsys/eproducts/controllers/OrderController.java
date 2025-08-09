package com.stmsys.eproducts.controllers;

import com.stmsys.eproducts.models.entities.Order;
import com.stmsys.eproducts.models.entities.OrderStatus;
import com.stmsys.eproducts.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Listado paginado con filtros opcionales: clientId, status
    @GetMapping
    public Page<Order> getAllOrders(Pageable pageable,
                                    @RequestParam(required = false) String clientId,
                                    @RequestParam(required = false) OrderStatus status) {

        if (clientId != null && status != null) {
            return orderService.getOrdersByClientIdAndStatus(clientId, status, pageable);
        } else if (clientId != null) {
            return orderService.getOrdersByClientId(clientId, pageable);
        } else if (status != null) {
            return orderService.getOrdersByStatus(status, pageable);
        } else {
            return orderService.getAllOrders(pageable);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        try {
            Order updated = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
