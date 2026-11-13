package com.productionready.nplusone.service;

import com.productionready.nplusone.dto.OrderResponse;
import com.productionready.nplusone.repository.PurchaseOrderRepository;
import org.springframework.data.domain.Pageable;
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

    public List<OrderResponse> findAll(Pageable pageable) {

        //Step #1 - Runs query #1 to get up to 10 Order IDs from PostgreSQL
        List<Long> ids = orders.findOrderIds(pageable);

        //Step #2 - Adds a quick guard clause
        if (ids.isEmpty()) return List.of();

        //Step #3 - Passes into Query #2
        return orders.findAllWithLinesByIds(ids).stream()
                .map(OrderResponse::from)
                .toList();
    }
}
