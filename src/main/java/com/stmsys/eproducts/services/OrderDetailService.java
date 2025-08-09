package com.stmsys.eproducts.services;

import com.stmsys.eproducts.models.entities.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    Page<OrderDetail> getAllOrderDetails(Pageable pageable);
    Optional<OrderDetail> getOrderDetailById(Long id);
    Page<OrderDetail> getOrderDetailsByOrderId(Long orderId, Pageable pageable);
    Page<OrderDetail> getOrderDetailsByProductId(Long productId, Pageable pageable);
    OrderDetail createOrderDetail(OrderDetail orderDetail);
    OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail);
    void deleteOrderDetail(Long id);
}
