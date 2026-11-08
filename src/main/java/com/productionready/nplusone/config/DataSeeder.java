package com.productionready.nplusone.config;

import com.productionready.nplusone.model.OrderLine;
import com.productionready.nplusone.model.PurchaseOrder;
import com.productionready.nplusone.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private static final int CUSTOMERS = 200;
    private static final int ORDERS = 1000;

    private final PurchaseOrderRepository orderRepository;
    private final int orderCount;

    DataSeeder(PurchaseOrderRepository orderRepository,
               @Value("${demo.seed.orders:1000}") int orderCount) {
        this.orderRepository = orderRepository;
        this.orderCount = orderCount;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Random random = new Random(30);

        List<PurchaseOrder> orders = new ArrayList<>(orderCount);
        for (int i = 1; i <= orderCount; i++) {
            String customerName = "Customer Name " + (1 + random.nextInt(200));
            Instant createdAt = Instant.now().minus(random.nextInt(365), ChronoUnit.DAYS);
            PurchaseOrder order = new PurchaseOrder(customerName, createdAt);

            int lineCount = 1 + random.nextInt(5);
            for (int j = 0; j < lineCount; j++) {
                order.addLine(new OrderLine(
                        "SKU-" + (1000 + random.nextInt(500)),
                        1 + random.nextInt(4),
                        BigDecimal.valueOf(500 + random.nextInt(20_000), 2)
                ));
            }
            orders.add(order);
        }
        orderRepository.saveAll(orders);

        log.info("Seeded {} orders", orderCount);
    }
}
