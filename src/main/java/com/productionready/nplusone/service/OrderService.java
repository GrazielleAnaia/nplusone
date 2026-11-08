package com.productionready.nplusone.service;

import com.productionready.nplusone.dto.OrderResponse;
import com.productionready.nplusone.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final PurchaseOrderRepository orders;

    public OrderService(PurchaseOrderRepository orders) {
        this.orders = orders;
    }

    public List<OrderResponse> findAll() {
        return orders.findAllWithLines().stream()
                .map(OrderResponse::from)
                .toList();
    }
}
