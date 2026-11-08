package com.productionready.nplusone.dto;

import com.productionready.nplusone.model.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long id, String customerName, List<Line> lines) {
    public record Line(String sku, int quantity, BigDecimal unitPrice) {
    }

    public static OrderResponse from(PurchaseOrder order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getLines().stream()
                        .map(l -> new Line(l.getSku(),
                                l.getQuantity(),
                                l.getUnitPrice()))
                        .toList());
    }
}
