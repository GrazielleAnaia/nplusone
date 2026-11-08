package com.productionready.nplusone;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:postgresql://localhost:5433/orders_test")
public class OrderQueryCountTest {
}
