package com.stmsys.eproducts.servicesImp;

import com.stmsys.eproducts.models.entities.OrderDetail;
import com.stmsys.eproducts.repositories.OrderDetailRepository;
import com.stmsys.eproducts.services.OrderDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {


    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Page<OrderDetail> getAllOrderDetails(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public Page<OrderDetail> getOrderDetailsByOrderId(Long orderId, Pageable pageable) {
        return orderDetailRepository.findByOrderId(orderId, pageable);
    }

    @Override
    public Page<OrderDetail> getOrderDetailsByProductId(Long productId, Pageable pageable) {
        return orderDetailRepository.findByProductId(productId, pageable);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail) {
        return orderDetailRepository.findById(id)
                .map(existing -> {
                    existing.setOrder(orderDetail.getOrder());
                    existing.setProduct(orderDetail.getProduct());
                    existing.setQuantity(orderDetail.getQuantity());
                    existing.setUnitPrice(orderDetail.getUnitPrice());
                    return orderDetailRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with id: " + id));
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
