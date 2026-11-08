package com.productionready.nplusone.controller;

import com.productionready.nplusone.dto.OrderResponse;
import com.productionready.nplusone.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    List<OrderResponse> findAll() {
        return service.findAll();
    }
}
