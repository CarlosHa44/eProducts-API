package com.stmsys.eproducts.controllers;

import com.stmsys.eproducts.models.entities.OrderDetail;
import com.stmsys.eproducts.services.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public Page<OrderDetail> getAllOrderDetails(Pageable pageable,
                                                @RequestParam(required = false) Long orderId,
                                                @RequestParam(required = false) Long productId) {

        if (orderId != null) {
            return orderDetailService.getOrderDetailsByOrderId(orderId, pageable);
        } else if (productId != null) {
            return orderDetailService.getOrderDetailsByProductId(productId, pageable);
        } else {
            return orderDetailService.getAllOrderDetails(pageable);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
        return orderDetail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@Valid @RequestBody OrderDetail orderDetail) {
        OrderDetail created = orderDetailService.createOrderDetail(orderDetail);
        return ResponseEntity.created(URI.create("/api/order-details/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @Valid @RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail updated = orderDetailService.updateOrderDetail(id, orderDetail);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}